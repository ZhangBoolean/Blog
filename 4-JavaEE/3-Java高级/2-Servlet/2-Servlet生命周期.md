一个Servlet的生命周期由 实例化，初始化，提供服务，销毁，被回收 几个步骤组成

1、实例化

当用户通过浏览器输入一个路径，这个路径对应的servlet被调用的时候，该Servlet就会被实例化

为LoginServlet显式提供一个构造方法 LoginServlet()

然后通过浏览器访问，就可以观察到
"LoginServlet 构造方法 被调用"

无论访问了多少次LoginServlet
LoginServlet构造方法 只会执行一次，所以Servlet是单实例的

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
     
    public LoginServlet(){
        System.out.println("LoginServlet 构造方法 被调用");
    }
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        //略
 
    }
}

2、初始化

LoginServlet 继承了HttpServlet，同时也继承了init(ServletConfig) 方法

init 方法是一个实例方法，所以会在构造方法执行后执行。

无论访问了多少次LoginSerlvet

init初始化 只会执行一次

import java.io.IOException;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
 
    public LoginServlet() {
        System.out.println("LoginServlet 构造方法 被调用");
    }
 
    public void init(ServletConfig config) {
        System.out.println("init(ServletConfig)");
    }
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // 略
 
    }
}

3、提供服务

接下来就是执行service()方法，然后通过浏览器传递过来的信息进行判断，是调用doGet()还是doPost()方法

在service()中就会编写我们的业务代码，在本例中就是判断用户输入的账号和密码是否正确

import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
 
    public LoginServlet() {
        System.out.println("LoginServlet 构造方法 被调用");
    }
 
    public void init(ServletConfig config) {
        System.out.println("init(ServletConfig)");
    }
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        String name = request.getParameter("name");
        String password = request.getParameter("password");
  
        String html = null;
  
        if ("admin".equals(name) && "123".equals(password))
            html = "<div style='color:green'>success</div>";
        else
            html = "<div style='color:red'>fail</div>";
  
        PrintWriter pw = response.getWriter();
        pw.println(html);
  
    }
}

4、销毁

接着是销毁destroy()
在如下几种情况下，会调用destroy()
1. 该Servlet所在的web应用重新启动
在server.xml中配置该web应用的时候用到了
 
<Context path="/" docBase="e:\\project\\j2ee\\web" debug="0" reloadable="false" />
 

如果把 reloadable="false" 改为reloadable="true" 就表示有任何类发生的更新，web应用会自动重启
当web应用自动重启的时候，destroy()方法就会被调用

2. 关闭tomcat的时候 destroy()方法会被调用，但是这个一般都发生的很快，不易被发现。

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
 
    public void destroy() {
        System.out.println("destroy()");
    }
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // 略
    }
 
}

5、被回收

当该Servlet被销毁后，就满足垃圾回收的条件了。 当下一次垃圾回收GC来临的时候，就有可能被回收。

这个也是不容易被观察到的现象。