

# [spring cloud config 配置中心](https://cloud.spring.io/spring-cloud-config/reference/html/)


思想就是:

1. 通过本项目配置中心通过约定规则去获取远程git服务器上的配置(当然 这里不限于git,也可以使用本地文件等方式)


https://blog.csdn.net/w_ya24k/article/details/79565269

然后客户端通过连接配置中心去读取git服务器上的统一配置

```java

/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties

```

但是:
客户端不能动态刷新

解决办法:
添加  @RefreshScope , 并且手动调用 客户端刷新地址


同时这种方式太lb了 ,  改进方式 引入bus 消息总线(或者后面的nacos )




开始 配置rabbit mq  , 这里使用docker 

```shell script
# 拉取镜像
docker pull rabbitmq:3.7.14-rc.1-management-alpine
# 运行镜像
docker run -d --name rbmq3.7.14 -p 15672:15672 -p 5672:5672  docker.io/rabbitmq:3.7.14-rc.1-management-alpine
```

然后可以通过IP:15672 端口 访问管理界面
默认用户名/密码为:guest


然后通过配置`spring.profiles.active` 配置多服务配置










