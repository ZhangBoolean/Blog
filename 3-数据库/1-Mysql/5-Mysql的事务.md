mysql事务

1、mysql中，事务其实是一个最小的的不可分割的工作单位。事务能够保证一个业务的完整性

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/1.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/2.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/3.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/4.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/5.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/6.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/7.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/8.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/9.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/10.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/11.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/12.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/13.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/14.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/15.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/16.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/17.png)

2、事务的四大特征：

原子性：事务是最小的单位，不可以再分割

一致性：事务要求，同一事务中的sql语句，必须保证同时成功或者公式失败

隔离性：事务1和事务2 之间是具有隔离性的

持久性：事务一旦结束（commit，rollback）,就不可以返回

事务开启：	

1、修改默认提交 set autocommit=0;

2、begin

3、start transaction;

事务手动提交：
commit;

事务手动回滚：
rollback;

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/18.png)

read uncommitted

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/19.png)

read committed

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/20.png)

repeatable read

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/21.png)

serializable

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/22.png)

![image](https://github.com/shaoshuaigege/Blog/blob/main/3-%E6%95%B0%E6%8D%AE%E5%BA%93/1-Mysql/img/img5/23.png)
