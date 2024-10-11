Spring事务什么时候会失效？

    访问权限问题
    
    方法用final修饰
    
    未被Spring管理
    
    错误的传播特性
    
    自己吞了异常
    
    手动抛了别的异常
    
    自定义了回滚异常
    
    方法内部调用



1、访问权限问题

Java的访问权限主要有三种：private、protected、public，它们的权限从左到右，依次变大。但如果我们在开发过程中，把有某些事务方法，定义了错误的访问权限，就会导致事务功能出问题，例如：

    1 @Service
    2 public class OrderService {
    3   @Transactional
    4     private void add(OrderVO orderVO) {
    5       saveData(orderVO);
    6   }
    7 }
复制代码
从上面可以看到add方法的访问权限是private修饰的，这样会导致事务失效，spring要求被代理方法必须是public的，事务会失效。



2、方法final修饰的

当某个方法被final修饰时，子类是无法继承和重载的，事务是基于动态代理去实现的，如果某个方法用final修饰了，那么在它的代理类中，就无法重写该方法，而添加事务功能。

    1 @Service
    2 public class OrderService {
    3   @Transactional
    4     public final void add(OrderVO orderVO) {
    5        saveData(orderVO);
    6   }
    7 }

3、未被Spring管理

使用spring事务的前提是：对象要被spring管理，需要创建bean实例。如果，你开发了一个Service类，但忘了加@Service注解，比如：


    1 //@Service
    2 public class OrderService {
    3   @Transactional
    4     public final void add(OrderVO orderVO) {
    5       saveData(orderVO);
    6   }
    7 }

又或者XML里面配置纳入Spring管理的包文件路径配置错误等。



4、错误的传播特性

我们在使用@Transactional注解时，是可以指定propagation参数的。该参数的作用是指定事务的传播特性，spring目前支持7种传播特性：
    
    REQUIRED：如果当前上下文中存在事务，那么加入该事务，如果不存在事务，创建一个事务，这是默认的传播属性值;
    
    SUPPORTS：如果当前上下文存在事务，则支持事务加入事务，如果不存在事务，则使用非事务的方式执行;
    
    MANDATORY：如果当前上下文中存在事务，否则抛出异常;
    
    REQUIRES_NEW：每次都会新建一个事务，并且同时将上下文中的事务挂起，执行当前新建事务完成以后，上下文事务恢复再执行;
    
    NOT_SUPPORTED：如果当前上下文中存在事务，则挂起当前事务，然后新的方法在没有事务的环境中执行;
    
    NEVER：如果当前上下文中存在事务，则抛出异常，否则在无事务环境上执行代码;
    
    NESTED：如果当前上下文中存在事务，则嵌套事务执行，如果不存在事务，则新建事务;

如果我们在手动设置propagation参数的时候，把传播特性设置错了，比如：


    1 @Service
    2 public class OrderService {
    3     @Transactional(propagation = Propagation.NEVER)
    4     public void add(OrderVO orderVO) {
    5          saveData(orderVO);
    6     }

5、自己吞了异常

事务不会回滚，最常见的问题是：开发者在代码中手动try...catch了异常。比如：


    1 @Service
    2 public class OrderService {
    3   @Transactional
    4     public void add(OrderVO orderVO) {
    5         try{
    6             saveData(orderVO);
    7         } catch(Exception e) {
    8             log.error(e);
    9      }
    10  }
    11 }

这种情况下spring事务当然不会回滚，因为开发者自己捕获了异常，又没有手动抛出，换句话说就是把异常吞掉了。

如果想要spring事务能够正常回滚，必须抛出它能够处理的异常。如果没有抛异常，则spring认为程序是正常的。



6、手动抛了别的异常

即使开发者没有手动捕获异常，但如果抛的异常不正确，spring事务也不会回滚。

    1 @Service
    2 public class OrderService {
    3   @Transactional
    4     public void add(OrderVO orderVO) {
    5         try{
    6             saveData(orderVO);
    7         } catch(Exception e) {
    8             log.error(e);
    9             throw new Exception(e);
    10     }   
    11  }
    12 }

上面的这种情况，开发人员自己捕获了异常，又手动抛出了异常：Exception，事务同样不会回滚。因为spring事务，默认情况下只会回滚RuntimeException（运行时异常）和Error（错误），对于普通的Exception（非运行时异常），它不会回滚。



7、自定义了回滚异常

@Transactional注解声明事务时，有时我们想自定义回滚的异常，spring也是支持的。可以通过设置rollbackFor参数，来完成这个功能。

    1 @Service
    2 public class OrderService {
    3     @Transactional(rollbackFor = BusinessException.class)
    4     public void add(OrderVO orderVO) {
    5         saveData(orderVO);
    6   }
    7 }

如果在执行上面这段代码，保存和更新数据时，程序报错了，抛了SqlException、NullPointerException等异常。而BusinessException是我们自定义的异常，报错的异常不属于BusinessException，所以事务也不会回滚。

8、方法内部调用

有时候我们需要在某个Service类的某个方法中，调用另外一个事务方法，比如：


    1 @Service
    2 public class OrderService {
    3   @Transactional
    4     public void add(OrderVO orderVO) {
    5          saveData(orderVO);
    6   }
    7
    8   @Transactional
    9     public void saveData(OrderVO orderVO) {
    10          doSameThing();
    11   }
    12 }

我们看到在事务方法add中，直接调用事务方法saveData。saveData方法拥有事务的能力是因为Spring Aop生成代理了对象，但是这种方法直接调用了this对象的方法，所以saveData方法不会生成事务。

由此可见，在同一个类中的方法直接内部调用，会导致事务失效。

 