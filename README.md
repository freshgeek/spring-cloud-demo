


# SpringCloud 示例工程





## 版本说明:H 版本 之后有停更

- 服务注册中心 -> Eureka (停更,不推荐用了)
  - Zookeeper
  - Consul
  - Nacos 重点

- 服务负载均衡 -> Ribbon (停更,但是还可以凑合用)
  - LoadBalancer

- 服务调用2 -> Feign 
  - OpenFeign

- 服务降级,熔断 -> Hystrix(部分老项目还在用)
  - resilience4j (国外推荐)
  - sentienl (推荐)

- 服务网关 -> Zuul
  - Zuul2(估计是出不来了,内部原因)
  - gateway
- 服务配置 -> Config
  - Nacos(阿里的,推荐)
  - 携程的apollo
- 服务总线 -> Bus
  - Nacos (阿里的推荐)
  
  
## eureka 服务

> 与zk+dubbo 类似



## openfeign 功能

提供一个接口化的方法直接访问 微服务

其中包含了robbin 负载均衡 , 同时为了可以监控请求响应可以打开 日志级别 查看详细请求日志



## Histrix 服务

### 1. 重要概念

- 服务降级 
- 服务熔断
- 服务限流


### 2. 注意

一般服务降级 在 `消费者端`  当然也可以在 `服务端-生产者`   提供兜底方法


### 3. 图形化界面




## 服务网关

- [zuul  netfix](https://github.com/Netflix/zuul) 过时，停更
- zuul2 netfix 开发中
- [gateway  spring开发 推荐](https://spring.io/projects/spring-cloud-gateway) 


### gateway 

该项目提供了一个在Spring生态系统之上构建的API网关，包括：Spring 5，Spring Boot 2和Project Reactor。Spring Cloud Gateway旨在提供一种简单而有效的方法来路由到API，并为它们提供跨领域的关注，例如：安全性，监视/指标和弹性。



