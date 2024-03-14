# **HttpServletRequest**

HttpServletRequest对象代表客户端的请求，当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中，开发人员通过这个对象的方法，可以获得客户这些信息。


HttpServletRequest常用方法：

1、获取客户机信息：

    a. getRequestURL()方法返回客户端发出请求时的完整URL(例如： http://localhost:8080/dyf-pms/helloServlet.action)
        StringBuffer requestURL = request.getRequestURL();
    b. getRequestURI()方法返回行中的资源名部分(例如：/dyf-pms/helloServlet.action)
        String requestURI = request.getRequestURI();
    c. getQueryString()方法返回请求行中的参数部分()
    d. getRemoteAddr()方法返回发出请求的客户机的IP地址
    e. getRemoteHost()方法返回发出请求的客户机的完整主机名
    f. getRemotePort()方法返回客户机所使用的网络端口号
    g. getLocalAddr()方法返回WEB服务器的IP地址
    h. getLocalName()方法返回WEB服务器的主机名
    i. getMethod()得到客户机请求方式
    j. getServletPath()返回用于匹配Servlet映射的URL部分(例如：/helloServlet.action)
        String servletPath = request.getServletPath();    

2、获得客户机请求头

    a. getHeader(name)方法,该方法作用是获取请求头中指定名称字段的值，如果没有该字段则返回null，如果有多个该指定名称的字段，则返回第一个字段的值
        String header = request.getHeader("host");
    b. getHeaders(String name)方法，返回一个Enumeration对象，该对象包含所有请求头中指定名称字段的值
    c. getHeaderNames方法,返回请求中所有头数据的名字的枚举，遍历所有可用头数据的好方式
        Enumeration<String> headerNames = request.getHeaderNames();
    d. getCharacterEncoding(),获取请求消息的实体部分的字符集编码，通常从Content-Type字段中截取

3、获得客户机请求参数(客户端提交的数据)

    a. getParameter(name):获取指定名称的参数值。这是最为常用的方法之一。
        String parameter1 = request.getParameter("demo");
    b. getParameterValues（String name）:获取指定名称参数的所有值数组。它适用于一个参数名对应多个值的情况。如页面表单中的复选框，多选列表提交的值。
        String[] parameter2 = request.getParameterValues("demo");
    c. getParameterNames():返回一个包含请求消息中的所有参数名的Enumeration对象。通过遍历这个Enumeration对象，就可以获取请求消息中所有的参数名。
        Enumeration<String> parameterNames = request.getParameterNames();
    d. getParameterMap():返回一个保存了请求消息中的所有参数名和值的Map对象。Map对象的key是字符串类型的参数名，value是这个参数所对应的Object类型的值数组
        Map<String, String[]> parameterMap = request.getParameterMap();

4、确定与请求内容相关的信息

    a. 获取请求的MIME(多用途互联网邮件扩展)内容类型
        String contentType = request.getContentType();
    b. 获取请求正文的长度
        int contentLength = request.getContentLength();
        long contentLengthLong = request.getContentLengthLong();//内容长度的超过2GB的请求
    c. 获取请求内容的字符编码
        String characterEncoding = request.getCharacterEncoding();
    
5、读取请求内容，不要在同一个请求上同时使用下面两种方法，会触发 java.lang.NullPointerException

    a. 适用于请求参数时二进制格式的
        ServletInputStream inputStream = request.getInputStream();
    b. 适用于请求参数是字符编码的
        BufferedReader reader = request.getReader();

    **第一次调用请求对象的getParameter、getParameterValues、getParameterMap、getParameterNames方法时
    Web容器将判断该请求是否包含post变量，如果包含它将读取请求的InputStream并解析这些变量，InputStream只能被读取一次
    如果在调用了一个含有post请求的getInputStream或getReader之后，再次尝试获取请求参数时则会触发一个java.lang.NullPointerException
    反之如果在获取了一个含有post变量的请求参数之后再调用getInputStream或getReader也会触发java.lang.NullPointerException
    任何时候在使用含有post变量的请求时，最好使用参数方法，不要使用getInputStream或getReader**

6、  
    request.setCharacterEncoding("utf-8");解决接收参数的中文乱码问题, 提示，写在 getParameter前