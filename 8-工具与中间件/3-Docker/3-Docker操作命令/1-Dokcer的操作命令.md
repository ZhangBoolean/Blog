操作情景：

    启动一个Nginx，并将他的首页改为自己的页面，发布出去，让所有人都能使用

    下载镜像->启动容器->修改页面->报错镜像->分享社区


![img.png](img/img1.png)

![img.png](img/img2.png)

![img.png](img/img3.png)

在下载指定应用的版本号时，需要到 [https://hub.docker.com/ ]() 去查询应用所包含的版本有哪些

![img.png](img/img4.png)

    点进去有详细的应用说明和执行操作的相关命令
![img.png](img/img5.png)

    Tags里面有应用的各种镜像，以及下载命令
![img_1.png](img/img6.png)

    下载指定版本镜像、查看所有镜像、 删除镜像 “docker rmi +应用名：版本号”  或者 “docker rmi + 应用的唯一ID”
![img.png](img/img7.png)

![img.png](img/img8.png)

![img.png](img/img9.png) 

![img.png](img/img10.png)

    ** docker rmi是删除镜像   docker rm是删除容器
![img.png](img/img11.png)

    服务器映射Docker的端口不能重复，服务器的端口必须唯一，不同Docker的端口可以重复（因为一个服务器可以部署多个独立的Docker）
![img.png](img/img12.png)

![img.png](img/img13.png)

![img.png](img/img14.png)

    将镜像打包成一个tar包
![img.png](img/img15.png)






