一、异常
1、什么是异常？

导致程序的正常流程被中断的事件，叫做异常

2、遇到过的异常
ParseException 解析异常，日期字符串转换为日期对象的时候，有可能抛出的异常
OutOfIndexException 数组下标越界异常
OutOfMemoryError 内存不足
ClassCastException 类型转换异常
ArithmeticException 除数为零
NullPointerException 空指针异常
FileNotFoundException 文件不存在异常



二、异常分类： 

可查异常，运行时异常和错误3种
其中，运行时异常和错误又叫非可查异常

1、可查异常

可查异常： CheckedException
可查异常即必须进行处理的异常，要么try catch住,要么往外抛，谁调用，谁处理，比如 FileNotFoundException
如果不处理，编译器，就不让你通过

package exception;
  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
  
public class TestException {
  
    public static void main(String[] args) {
          
        File f= new File("d:/LOL.exe");
          
        try{
            System.out.println("试图打开 d:/LOL.exe");
            new FileInputStream(f);
            System.out.println("成功打开");
        }
        catch(FileNotFoundException e){
            System.out.println("d:/LOL.exe不存在");
            e.printStackTrace();
        }
          
    }
}

2、运行时异常

运行时异常RuntimeException指： 不是必须进行try catch的异常
常见运行时异常:
除数不能为0异常:ArithmeticException
下标越界异常:ArrayIndexOutOfBoundsException
空指针异常:NullPointerException
在编写代码的时候，依然可以使用try catch throws进行处理，与可查异常不同之处在于，即便不进行try catch，也不会有编译错误
Java之所以会设计运行时异常的原因之一，是因为下标越界，空指针这些运行时异常太过于普遍，如果都需要进行捕捉，代码的可读性就会变得很糟糕。

package exception;
  
public class TestException {
  
    public static void main(String[] args) {
         
        //任何除数不能为0:ArithmeticException
        int k = 5/0;
         
        //下标越界异常：ArrayIndexOutOfBoundsException
        int j[] = new int[5];
        j[10] = 10;
         
        //空指针异常：NullPointerException
        String str = null;
        str.length();
   }
}

3、错误

错误Error，指的是系统级别的异常，通常是内存用光了
在默认设置下，一般java程序启动的时候，最大可以使用16m的内存
如例不停的给StringBuffer追加字符，很快就把内存使用光了。抛出OutOfMemoryError
与运行时异常一样，错误也是不要求强制捕捉的

package exception;
  
public class TestException {
  
    public static void main(String[] args) {
     
        StringBuffer sb =new StringBuffer();
         
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sb.append('a');
        }
         
    }
 
}

4、三种分类

总体上异常分三类：
1. 错误
2. 运行时异常
3. 可查异常

5、练习-异常分类 

运行时异常 RuntimeException，能否被捕捉？

错误Error，能否被捕捉？

面试题常问题：: 运行时异常与非运行时异常的区别：
运行时异常是不可查异常，不需要进行显式的捕捉
非运行时异常是可查异常，必须进行显式的捕捉，或者抛出

不要答成：
运行时异常是运行的时候抛出的异常，非运行时异常，不运行也能抛出

package exception;
 
public class TestException {
 
    public static void main(String[] args) {
 
        String str = null;
 
        try {
            str.toString();
        } catch (NullPointerException e) {
            System.out.println("捕捉到运行时异常: NullPointerException ");
        }
 
        StringBuffer sb = new StringBuffer("1234567890");
        try {
            for (int i = 0; i < 100; i++) {
                sb.append(sb.toString());
            }
        } catch (OutOfMemoryError e) {
            System.out.println("捕捉到内存用光错误:  OutOfMemoryError");
        }
 
    }
}

三、异常分类扩展

在 Java 中，所有的异常都有一个共同的祖先 Throwable（可抛出）。Throwable 指定代码中可用异常传播机制通过 Java 应用程序传输的任何问题的共性。
       Throwable： 有两个重要的子类：Exception（异常）和 Error（错误），二者都是 Java 异常处理的重要子类，各自都包含大量子类。

       Error（错误）:是程序无法处理的错误，表示运行应用程序中较严重问题。大多数错误与代码编写者执行的操作无关，而表示代码运行时 JVM（Java 虚拟机）出现的问题。例如，Java虚拟机运行错误（Virtual MachineError），当 JVM 不再有继续执行操作所需的内存资源时，将出现 OutOfMemoryError。这些异常发生时，Java虚拟机（JVM）一般会选择线程终止。

。这些错误表示故障发生于虚拟机自身、或者发生在虚拟机试图执行应用时，如Java虚拟机运行错误（Virtual MachineError）、类定义错误（NoClassDefFoundError）等。这些错误是不可查的，因为它们在应用程序的控制和处理能力之 外，而且绝大多数是程序运行时不允许出现的状况。对于设计合理的应用程序来说，即使确实发生了错误，本质上也不应该试图去处理它所引起的异常状况。在 Java中，错误通过Error的子类描述。

       Exception（异常）:是程序本身可以处理的异常。

       Exception 类有一个重要的子类 RuntimeException。RuntimeException 类及其子类表示“JVM 常用操作”引发的错误。例如，若试图使用空值对象引用、除数为零或数组越界，则分别引发运行时异常（NullPointerException、ArithmeticException）和 ArrayIndexOutOfBoundException。

   注意：异常和错误的区别：异常能被程序本身可以处理，错误是无法处理。

   通常，Java的异常(包括Exception和Error)分为可查的异常（checked exceptions）和不可查的异常（unchecked exceptions）。
      可查异常（编译器要求必须处置的异常）：正确的程序在运行中，很容易出现的、情理可容的异常状况。可查异常虽然是异常状况，但在一定程度上它的发生是可以预计的，而且一旦发生这种异常状况，就必须采取某种方式进行处理。

      除了RuntimeException及其子类以外，其他的Exception类及其子类都属于可查异常。这种异常的特点是Java编译器会检查它，也就是说，当程序中可能出现这类异常，要么用try-catch语句捕获它，要么用throws子句声明抛出它，否则编译不会通过。

     不可查异常(编译器不要求强制处置的异常):包括运行时异常（RuntimeException与其子类）和错误（Error）。

     Exception 这种异常分两大类运行时异常和非运行时异常(编译异常)。程序中应当尽可能去处理这些异常。

       运行时异常：都是RuntimeException类及其子类异常，如NullPointerException(空指针异常)、IndexOutOfBoundsException(下标越界异常)等，这些异常是不检查异常，程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的，程序应该从逻辑角度尽可能避免这类异常的发生。

      运行时异常的特点是Java编译器不会检查它，也就是说，当程序中可能出现这类异常，即使没有用try-catch语句捕获它，也没有用throws子句声明抛出它，也会编译通过。
       非运行时异常 （编译异常）：是RuntimeException以外的异常，类型上都属于Exception类及其子类。从程序语法角度讲是必须进行处理的异常，如果不处理，程序就不能编译通过。如IOException、SQLException等以及用户自定义的Exception异常，一般情况下不自定义检查异常。