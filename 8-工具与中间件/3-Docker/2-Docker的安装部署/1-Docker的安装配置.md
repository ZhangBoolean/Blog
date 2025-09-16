
一、Docker的安装配置

1、安装Docker

国内常见云平台：
● 阿里云、腾讯云、华为云、青云......
使用 CentOS 7.9
WindTerm下载：https://github.com/kingToolbox/WindTerm/releases/download/2.6.0/WindTerm_2.6.1_Windows_Portable_x86_64.zip

    # 移除旧版本docker
    sudo yum remove docker \
    docker-client \
    docker-client-latest \
    docker-common \
    docker-latest \
    docker-latest-logrotate \
    docker-logrotate \
    docker-engine
    
    # 配置docker yum源。
    sudo yum install -y yum-utils
    sudo yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    
    
    # 安装 最新 docker
    sudo yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    
    # 启动& 开机启动docker； enable + start 二合一
    systemctl enable docker --now
    
    # 配置加速
    sudo mkdir -p /etc/docker
    sudo tee /etc/docker/daemon.json <<-'EOF'
    {
    "registry-mirrors": [
    "https://mirror.ccs.tencentyun.com"
    ]
    }
    EOF
    sudo systemctl daemon-reload
    sudo systemctl restart docker


2、命令

    #查看运行中的容器
    docker ps
    #查看所有容器
    docker ps -a
    #搜索镜像
    docker search nginx
    #下载镜像
    docker pull nginx
    #下载指定版本镜像
    docker pull nginx:1.26.0
    #查看所有镜像
    docker images
    #删除指定id的镜像
    docker rmi e784f4560448
    
    
    #运行一个新容器
    docker run nginx
    #停止容器
    docker stop keen_blackwell
    #启动容器
    docker start 592
    #重启容器
    docker restart 592
    #查看容器资源占用情况
    docker stats 592
    #查看容器日志
    docker logs 592
    #删除指定容器
    docker rm 592
    #强制删除指定容器
    docker rm -f 592
    # 后台启动容器
    docker run -d --name mynginx nginx
    # 后台启动并暴露端口
    docker run -d --name mynginx -p 80:80 nginx
    # 进入容器内部
    docker exec -it mynginx /bin/bash
    
    # 提交容器变化打成一个新的镜像
    docker commit -m "update index.html" mynginx mynginx:v1.0
    # 保存镜像为指定文件
    docker save -o mynginx.tar mynginx:v1.0
    # 删除多个镜像
    docker rmi bde7d154a67f 94543a6c1aef e784f4560448
    # 加载镜像
    docker load -i mynginx.tar
    
    
    # 登录 docker hub
    docker login
    # 重新给镜像打标签
    docker tag mynginx:v1.0 leifengyang/mynginx:v1.0
    # 推送镜像
    docker push leifengyang/mynginx:v1.0
