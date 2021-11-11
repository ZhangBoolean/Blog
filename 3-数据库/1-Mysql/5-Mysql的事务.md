mysql事务
1、mysql中，事务其实是一个最小的的不可分割的工作单位。事务能够保证一个业务的完整性

































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


read uncommitted

read committed

repeatable read

serializable



