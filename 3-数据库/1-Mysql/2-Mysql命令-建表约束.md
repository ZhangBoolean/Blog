sql分类
DDL（操作数据库、表）
DML（增删改表中的数据）
DQL（查询表中的数据）
DCL（授权）

mysql建表约束
1）主键约束（ primary key）：它能够唯一确定一张表中的一条记录，也就是我们通过给某个字段添加约束，就可以使得该字段不重复且不为空。
name varchar(20) primary key; 
***联合主键：只要联合的主键加起来不重复就可以***
create table user2（
id int,
name varchar(20),
password varchar(20),
primary key(id,name)
);

2）自增约束(primary key auto_increment  和主键约束搭配在一起使用)
create table user3(
id int primary key auto_increment,
name varchar(20)
);
***建表后操作约束****
1、添加约束   alter table user4 add primary key (id);
2、删除约束   alter table user4 drop primary key(id);
3、修改约束   alter table user4 modify id int primary key;

3)唯一约束(unique):约束修饰的值不可以重复
create table user5（
id int,
name varchar(20)；
unique (id，name)
);
建表后操作 
   alter table user5 add unique(id，name);

4)非空约束(not null)：修饰的字段不能为空 NULL
create table user6(
id int，
name varchat(20) not null
);

5)默认约束(default):就是当我们插入字段的时候，如果没有传值，就会使用默认值
create table user10(
id int,
name varchar(20),
age int default 10
);

6)外键约束
--涉及到两个表：父表（主表），子表（副表）

--班级表(主表)
create table classes(
id int primary key,
name varchar(20)
)default charset'utf8';

--学生表(副表)
create table students(
id int primary key,
name varchar(20),
class_id int,
foreign key(class_id) references classes(id)
)default charset 'utf8';

insert into classes values (1,'一班');
insert into classes values (2,'二班');
insert into classes values (3,'三班');
insert into classes values (4,'四班');

insert into students values(1001,'张三',1);
insert into students values(1002,'李四',2);
insert into students values(1003,'王五',3);
insert into students values(1004,'六时',4);

insert into students values(1005,'七才',5);         【错误】
1、[主表classes中没有的数据值，在副表中，是不可以使用的]
delect from classes where id=4;                         【错误】
2、[主表中的记录被副表引用，是不可以删除的]	




















