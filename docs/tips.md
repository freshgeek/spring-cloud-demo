

##提示

为了兼容性,项目中统一使用域名,可以在本机hosts中配置对应的地址

127.0.0.1 eureka01
127.0.0.1 eureka02
- 这是目标安装各种服务的虚拟机的IP
x.x.x.x local
192.168.203.102 zookeeper
192.168.203.102 consul


同时提供了启动文件 , 配置好依赖即可启动测试 

![](img/configuration-save-muti-services.png)

注意:需要ide 2019.3 later 版本才能出现
![](img/configuration-save-local.png)



