页面跳转是开发一个web应用经常会发生的事情。

比如登录成功或是失败后，分别会跳转到不同的页面。

跳转的方式有两种，服务端跳转和客户端跳转

1、服务端跳转

在Servlet中进行服务端跳转的方式：

request.getRequestDispatcher("success.html").forward(request, response);

服务端跳转可以看到浏览器的地址依然是/login 路径，并不会改变路径

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String name = request.getParameter("name");
        String password = request.getParameter("password");
 
        if ("admin".equals(name) && "123".equals(password)) {
            request.getRequestDispatcher("success.html").forward(request, response);
        }
    } 
}

2、客户端跳转

在Servlet中进行客户端跳转的方式：
 
response.sendRedirect("fail.html");

可以观察到，浏览器地址发生了变化

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class LoginServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String name = request.getParameter("name");
        String password = request.getParameter("password");
 
        if ("admin".equals(name) && "123".equals(password)) {
            request.getRequestDispatcher("success.html").forward(request, response);
        }
        else{
            response.sendRedirect("fail.html");
        }
    }
}