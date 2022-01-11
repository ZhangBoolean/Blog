有的时候会有这样的业务需求：

tomcat一启动，就需要执行一些初始化的代码，比如校验数据库的完整性等。

但是Servlet的生命周期是在用户访问浏览器对应的路径开始的。如果没有用户的第一次访问，就无法执行相关代码。

这个时候，就需要Servlet实现自启动 即，伴随着tomcat的启动，自动启动初始化，在初始化方法init()中，就可以进行一些业务代码的工作了。

load-on-startup

在web.xml中，配置Hello Servlet的地方，增加一句
 
<load-on-startup>10</load-on-startup>
 

取值范围是1-99

即表明该Servlet会随着Tomcat的启动而初始化。

同时，为HelloServlet提供一个init(ServletConfig) 方法，验证自启动

如图所示，在tomcat完全启动之前，就打印了init of HelloServlet
<load-on-startup>10</load-on-startup> 中的10表示启动顺序
如果有多个Servlet都配置了自动启动，数字越小，启动的优先级越高

<?xml version="1.0" encoding="UTF-8"?>
<web-app>
 
    <servlet>
        <servlet-name>HelloServlet</servlet-name>
        <servlet-class>HelloServlet</servlet-class>
        <load-on-startup>10</load-on-startup>
    </servlet>
 
    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>
     
    <servlet>
        <servlet-name>LoginServlet</servlet-name>
        <servlet-class>LoginServlet</servlet-class>
    </servlet>
 
    <servlet-mapping>
        <servlet-name>LoginServlet</servlet-name>
        <url-pattern>/login</url-pattern>
    </servlet-mapping>   
 
</web-app>