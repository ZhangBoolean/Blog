    a、各个业务模块将需要发送到邮件数据存储到 tn_email_info表中
    b、对邮箱服务进行相关设置
    c、根据时间状态判断出需要 发送、修改业务数据邮件 （多线程的运用）
    (发送的邮件较多，分为list、ready两种类型，防止一次发送过多导致超时)
    d、发送邮件



tn_email_info
![img.png](img/4.png)



最终发送方法
![img.png](img/5.png)