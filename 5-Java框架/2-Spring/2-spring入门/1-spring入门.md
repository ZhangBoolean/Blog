一、思考

1、对象是谁创建的 ?  

对象是由Spring创建的

2、对象的属性是怎么设置的 ?  

对象的属性是由Spring容器设置的

3、这个过程就叫控制反转 :

    控制 : 谁来控制对象的创建 , 传统应用程序的对象是由程序本身控制创建的 , 使用Spring后 , 对象是由Spring来创建的

    反转 : 程序本身不创建对象 , 而变成被动的接收对象 .

    依赖注入 : 就是利用set方法来进行注入的.

    IOC是一种编程思想，由主动的编程变成被动的接收

二、Spring配置

1、别名

alias 设置别名 , 为bean设置别名 , 可以设置多个别名

<!--设置别名：在获取Bean的时候可以使用别名获取-->
<alias name="userT" alias="userNew"/>

2、Bean的配置

<!--bean就是java对象,由Spring创建和管理-->

<!--
   id 是bean的标识符,要唯一,如果没有配置id,name就是默认标识符
   如果配置id,又配置了name,那么name是别名
   name可以设置多个别名,可以用逗号,分号,空格隔开
   如果不配置id和name,可以根据applicationContext.getBean(.class)获取对象;

class是bean的全限定名=包名+类名
-->
<bean id="hello" name="hello2 h2,h3;h4" class="com.kuang.pojo.Hello">
   <property name="name" value="Spring"/>
</bean>

3、import

团队的合作通过import来实现 .

<import resource="{path}/beans.xml"/>