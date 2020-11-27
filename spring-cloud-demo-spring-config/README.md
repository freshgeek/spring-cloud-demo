

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



## 开始 配置rabbit mq  , 这里使用docker 安装部署

```shell script
# 拉取镜像
docker pull rabbitmq:3.7.14-rc.1-management-alpine
# 运行镜像
docker run -d --name rbmq3.7.14 -p 15672:15672 -p 5672:5672  docker.io/rabbitmq:3.7.14-rc.1-management-alpine
```

然后可以通过IP:15672 端口 访问管理界面 , 默认用户名/密码为:`guest`

然后通过配置`spring.profiles.active` 配置多服务配置


## 两种设计通知思想

1. 使用客户端通知其他客户端
2. 由配置中心刷新通知其他客户端 (使用这种)

两种方案都可行,但是从微服务的思想来说应该是由配置中心作为通知方去通知客户端,而不应该耦合给客户端去做

### 第二种设计实现

在配置中心`spring-config` 配置mq(这里spring 只支持rabbitmq , Kafka ) bus ,并暴露刷新点

```yaml
#rabbitmq相关配置
rabbitmq:
  host: local
  port: 5672
  username: guest
  password: guest

#rabbitmq相关配置,暴露bus刷新配置的端点
management:
  endpoints: #暴露bus刷新配置的端点
    web:
      exposure:
        include: 'bus-refresh'
```

然后 `客户端 , 服务端 `需要mq  所以引入pom
```xml
<!--        添加消息总线RabbitMQ支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
```

3. 在客户端配置yml

```yaml
  #rabbitmq相关配置 15672是Web管理界面的端口；5672是MQ访问的端口
  rabbitmq:
    host: local
    port: 5672
    username: guest
    password: guest
# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

此时可以测试,在git 上修改内容 配置中心与客户端同步刷新最新值

其中通知方式有两种:
1. 全局通知 ``
2. 定点通知 ``

> 可以在启动参数添加端口`-Dserver.port=3355/3366` , 都引用同一份yml , 测试全局和定点效果





