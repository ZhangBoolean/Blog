sql����
DDL���������ݿ⡢��
DML����ɾ�ı��е����ݣ�
DQL����ѯ���е����ݣ�
DCL����Ȩ��

mysql����Լ��
1������Լ���� primary key�������ܹ�Ψһȷ��һ�ű��е�һ����¼��Ҳ��������ͨ����ĳ���ֶ����Լ�����Ϳ���ʹ�ø��ֶβ��ظ��Ҳ�Ϊ�ա�
name varchar(20) primary key; 
***����������ֻҪ���ϵ��������������ظ��Ϳ���***
create table user2��
id int,
name varchar(20),
password varchar(20),
primary key(id,name)
);

2������Լ��(primary key auto_increment  ������Լ��������һ��ʹ��)
create table user3(
id int primary key auto_increment,
name varchar(20)
);
***��������Լ��****
1�����Լ��   alter table user4 add primary key (id);
2��ɾ��Լ��   alter table user4 drop primary key(id);
3���޸�Լ��   alter table user4 modify id int primary key;

3)ΨһԼ��(unique):Լ�����ε�ֵ�������ظ�
create table user5��
id int,
name varchar(20)��
unique (id��name)
);
�������� 
   alter table user5 add unique(id��name);

4)�ǿ�Լ��(not null)�����ε��ֶβ���Ϊ�� NULL
create table user6(
id int��
name varchat(20) not null
);

5)Ĭ��Լ��(default):���ǵ����ǲ����ֶε�ʱ�����û�д�ֵ���ͻ�ʹ��Ĭ��ֵ
create table user10(
id int,
name varchar(20),
age int default 10
);

6)���Լ��
--�漰�������������������ӱ�����

--�༶��(����)
create table classes(
id int primary key,
name varchar(20)
)default charset'utf8';

--ѧ����(����)
create table students(
id int primary key,
name varchar(20),
class_id int,
foreign key(class_id) references classes(id)
)default charset 'utf8';

insert into classes values (1,'һ��');
insert into classes values (2,'����');
insert into classes values (3,'����');
insert into classes values (4,'�İ�');

insert into students values(1001,'����',1);
insert into students values(1002,'����',2);
insert into students values(1003,'����',3);
insert into students values(1004,'��ʱ',4);

insert into students values(1005,'�߲�',5);         ������
1��[����classes��û�е�����ֵ���ڸ����У��ǲ�����ʹ�õ�]
delect from classes where id=4;                         ������
2��[�����еļ�¼���������ã��ǲ�����ɾ����]	




















