# SpringCloud 示例工程

本项目记录了springcloud 技术栈的学习示例与讲解,提供示例代码.
其中包含了老的技术选型,如:eureka,ribbon,hystrix,zuul等老项目可能在用的,以及新的alibaba,nacos,sentienl等技术,
如有不妥欠缺之处,欢迎大家以Issue提出

## 前提约定
本项目使用Idea,同时使用`-Dserver.port=` 添加虚拟机端口方式和指定配置环境`profile`方式替代复制多个模块方式演示不同环境/集群情况

配置启动方式如图(后面不一一介绍)：
![配置启动方式](../img/configure.jpg)


## 涉及微服务架构技术点:

- [服务发现](service-discovery.md)
    - [Eureka](service-discovery-eureka.md)
    - [Zookeeper](service-discovery-zookeeper.md)
    - [Consul](service-discovery-consul.md)
    - [Nacos-discovery](service-discovery-nacos.md)
- 服务负载均衡
    - Ribbon 
    - Loadbalancer
- 服务调用
    - Feign
    - Open Feign
- 服务降级熔断限流
    - Hystrix
    - Sentienl
- 服务网关
    - Zuul
    - Gateway
- 服务配置
    - Spring Config
    - Nacos Config

- 服务总线
    - Bus
    - Nacos
    
- 消息驱动Stream

- 链路追踪
    - Sleuth + Zipkin

- 全局事务Seate

        






