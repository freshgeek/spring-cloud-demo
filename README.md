# spring-cloud-demo

#### 介绍
跟着视频学习的全部示例,包含SpringCloud 生态的各种解决方案(包含流行,停更,以后推荐等)的示例代码

同时包含了自己的一些思考+踩坑，如：

- 每个中间件的部署说明
- 规划抽取公共类-通用接口
- 用AOP解决服务降级带来的兜底方法耦合与实现问题
- 使用Hystrix Dashboard 监控集群问题
- Nacos 集成外部数据源
- Nacos docker集群+VIP部署
- 实现Sentinel界面配置后自动推送Nacos（不需要在Nacos配置）
- 完成Seata-Nacos-Db-Client 整合搭建



## 前提准备

配置中均使用域名连接，以下是我的hosts映射：

127.0.0.1 eureka01

127.0.0.1 eureka02


这是目标安装各种服务的虚拟机的IP:


- 192.168.203.102 zookeeper

- 192.168.203.102 consul

- 192.168.203.102 nacos

- 192.168.203.102 sentinel

- 192.168.203.102 rabbitmq

- 192.168.203.102 zipkin

- 192.168.203.102 seata


## [已开启 git page ， 请直接访问 ](https://pocg.gitee.io/spring-cloud-demo/)
![已开启 git page ， 请直接访问](https://pocg.gitee.io/spring-cloud-demo/img/md-covery-demo.jpg)

## 提供启动配置类方便测试

![](https://pocg.gitee.io/spring-cloud-demo/img/configuration-save-muti-services.png)