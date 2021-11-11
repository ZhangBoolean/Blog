Mysql的四种连接查询

内连接
内连查询，其实就是两张表中的数据，通过某个字段相对，查询出相关记录数据
inner join 或者 join

外连接
1、左连接 
会把左边表里面的所有数据取出来，而右边表中的数据，如果有相等的，就显示出来；如果没有就会补NULL
left join 或者 left outer join

2、右连接 
会把右边表里面的所有数据取出来，而左边表中的数据，如果有相等的，就显示出来；如果没有就会补NULL
right join 或者 right outer join

3、完全外链接 
full join 或者 full outer join
Mysql不支持完全连接，但是可以用union连接左右外连接实现

