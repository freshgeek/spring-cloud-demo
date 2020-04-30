
## eureka 集群

相互注册 相互守望

其实就是多个单机版 注册监听其他所有eureka




## 运行集群

1. 首先复制 yml,其他参数不用修改,只需要修改以下两项

```

eureka:
  instance:

eureka:
  instance:
# 主机名不能重名 , 所以需要 配置host 映射
    hostname: eureka01
  client:
    service-url:
# 配置其他eureka , 多个则逗号隔开
      defaultZone: http://eureka02:8762/eureka/


```


2. hosts 文件 
> Windows C:\Windows\System32\drivers\etc
> mac 百度
>
```

##### spring cloud eureka
127.0.0.1 eureka01
127.0.0.1 eureka02

```

3. 设置启动配置,添加启动参数 指定配置文件




## eureka 客户端 主机名

```yaml

eureka:
  instance:
    hostname: payment8001
#     instance-id:

```
    
    
## 显示IP

```yaml

eureka:
   instance: 
      prefer-ip-address: true

```


## eureka 高可用

cap 理论中的ap 分支

好死不如赖活着 ,  在一定时间内 会保留掉线的service 


在服务端 可以关闭

```yaml

#关闭自我保护 默认是true
  server:
    enable-self-preservation: false
# 时间 单位毫秒
    eviction-interval-timer-in-ms: 10



```


在客户端 可以调整心跳间隔
```yaml

eureka:
  instance:
# eureka 客户端向服务端发送心跳间隔 默认30s
    lease-renewal-interval-in-seconds: 1
# eureka 服务端收到最后一次心跳后等待时间上限 默认90s
    lease-expiration-duration-in-seconds: 2

```