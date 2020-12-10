
#  Nacos

（Nacos）Dynamic Naming and Configuration Service 动态命名和配置服务 
一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
- [https://nacos.io/zh-cn/docs/what-is-nacos.html](https://nacos.io/zh-cn/docs/what-is-nacos.html)

>  简单来说 ：Nacos = Eureka+Config+Bus 注册中心与配置中心的组合


# 1. 搭建单机 nacos

> [https://nacos.io/zh-cn/docs/quick-start-docker.html](https://nacos.io/zh-cn/docs/quick-start-docker.html)
>

我这里选的是单机版本Derby 省得去配置数据库
> 需要下载配置好`docker` `docker-compose`

```shell script
git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
docker-compose -f example/standalone-derby.yaml up -d
```
启动完成之后可以访问 : [http://127.0.0.1:8848/nacos/](http://127.0.0.1:8848/nacos/) , 账号/密码: `nacos`

## 1.2 服务注册发现-服务端和客户端加入pom

这里就直接改造`spring-cloud-demo-provider-payment` 和 `spring-cloud-demo-consumer-order` ,把之前的配置中心注释掉，添加nacos

```xml
        <!--微服务nacos 服务发现 选一 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
```


## 1.3 服务提供(spring-cloud-demo-provider-payment)配置yml(application-nacos.yml)

```yaml

spring:
  application:
    name: nacos-payment-service
  cloud:
    nacos:
      discovery:
        #Nacos服务注册中心地址
        server-addr: local:8848
## 
management:
  endpoints:
    web:
      exposure:
        include: '*'

```

## 1.4 然后只需要配置注解`@EnableDiscoveryClient`

这一步就完成了服务提供者的注解，选中profile 启动即可

## 1.5 然后告诉消费者`spring-cloud-demo-consumer-order` 去nacos获取服务

1. 和上面一致加入pom
2. 修改yml表明nacos地址
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: local:8848
  application:
    name: cloud-consumer-order
```
3. 加入启动类注解`@EnableDiscoveryClient`

4. 业务类 

指定nacos的服务提供者名字即可自动寻址，这点和eureka一样

```java
package top.freshgeek.springcloud.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;

import javax.annotation.Resource;

/**
 * @author chen.chao
 */
@RequestMapping("/nacos/")
@RestController
public class OrderNacosController {

	final String service = "http://nacos-payment-service";

	@Resource
	private RestTemplate restTemplate;


	@GetMapping("payment/get/{id}")
	public CommonResult getPayment(@PathVariable("id") int id) {
		return restTemplate.getForObject(service + "/payment/get/" + id, CommonResult.class);
	}

	@PostMapping("payment/create")
	public CommonResult getPayment(Payment payment) {
		return restTemplate.postForObject(service + "/payment/create",
				payment, CommonResult.class);
	}

}

```

## 1.7 负载均衡
可以自动集成ribbon负载均衡，与之前的eureka方式一致


## 特性
nacos 支持CAP 理论中的 AP+CP 两者可以根据实际需求切换，不能同时支持


# nacos 配置中心 

等同于nacos 作为原本的`spring-cloud-demo-spring-config` 配置中心，
客户端使用直接连nacos,方式和理念都差不多，
只不过nacos 消除了bus 总线天生集成了自动通知，这里简单演示一下


## 使用配置
创建模块 spring-cloud-demo-nacos-config 

1. 引入pom

```xml
        <!--nacos-config-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <!--nacos-discovery-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!--web + actuator-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--一般基础配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>


```

2. 写配置文件
一般开发场景都是把配置文件中心化，统一由配置中心配置，应用读取后再启动，因此这里也是使用 `bootstrap.yml`


```yaml

# nacos配置
spring:
  profile:
    active: dev
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: local:8848 #Nacos服务注册中心地址
      config:
        server-addr: local:8848 #Nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
# ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
# nacos-config-client-dev.yaml


```
 
> 这里需要配置一下nacos文件nacos-config-client-dev.yaml
>
![1234](img/nacos-config-demo.jpg)
 
```yaml
server:
    port:3377
config:
    info: 1234
```


3. 加上启动类注解`@EnableDiscoveryClient`后一样写个key controller

```java

package top.freshgeek.springcloud.nacos.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen.chao
 */
@RefreshScope
@RestController
public class ConfigController {
	@Value("${config.info}")
	private String configInfo;

	@GetMapping("/configInfo")
	public String getConfigInfo() {
		return configInfo;
	}
}

```

4. 访问路径获取配置，然后修改nacos，测试发布
![监听刷新](img/nacos-config-refresh.jpg)

5.再次访问路径，可以看到已经刷新了,这样就完成了配置动态刷新

> 但是其实这里还有点不足，这边 nacos 不能提供灰度发布，和权限控制没有Apollo 强

## nacos 配置分空间，分组
> [相关概念 https://nacos.io/zh-cn/docs/concepts.html](https://nacos.io/zh-cn/docs/concepts.html)

nacos 中有命名空间,默认是public，可以创建其他空间

> 官方介绍： 用于进行租户粒度的配置隔离。不同的命名空间下，可以存在相同的 Group 或 Data ID 的配置。Namespace 的常用场景之一是不同环境的配置的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等。                                

![](img/nacos-namespace.jpg)
还有分组的概念，默认是DEFAULT_GROUP，也可以指定创建其他空间
> 
![](img/nacos-group.jpg)

## 接着两个问题
1. 如果Nacos挂掉怎么办
2. Nacos停机了，但是有些数据仍需要保留

### 1. 配置持久化

配置信息一般是比较多的，并且可能nacos出现故障启动之后不能让配置信息消失，因此需要加入持久化
官方推荐的是MySQL ，并且最少需要主从备份,这里就直接连个单机MySQL


```shell script
docker run -d \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=data.keepon.show \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=springcloud \
-e MYSQL_SERVICE_PASSWORD=springcloud \
-e MYSQL_SERVICE_DB_NAME=nacos_db \
-p 8848:8848 \
--restart=always \
--name nacos-standalone-mysql-docker \
nacos/nacos-server
```

> 附上初始化脚本：[nacos-mysql.sql](https://github.com/alibaba/nacos/blob/master/distribution/conf/nacos-mysql.sql)
> 具体配置文档属性说明：[https://nacos.io/zh-cn/docs/quick-start-docker.html](https://nacos.io/zh-cn/docs/quick-start-docker.html)
> 主从备份搭建可以看我的博文[MySQL 主从复制搭建 一主一从环境搭建](https://blog.csdn.net/qq_35530042/article/details/107437484)

官方docker-compose 的版本只能连docker自动创建的mysql，我也贴一下

```shell script
git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
docker-compose -f example/standalone-mysql-5.7.yaml up
```

可以在配置中心添加一个配置，然后在数据库中查看已经出现了这一条记录，我的版本是在 `config_info` 这个表


这样，如果应用挂了，就重启后就直接从mysql 中读取配置信息了，这样稳定性就增强了很多，基本上很多中小厂就完全够用了

### 2. Nacos消除单点故障，集群部署

虽然加了MySQL,但是还存在单点故障，因为虽然我们的nacos 集成了配置中心+服务发现，
但是带来的问题就是不能挂，如果注册服务发现挂了，整个微服务就瘫痪了 ， 因此集群部署也是必须做的

> [https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html](https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html)

这里直接用docker-compose 构建一下,机器太小了，弄不出那么多的虚拟机，在一个虚拟机上也没啥意思 ， 以后补上

```shell script

git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
docker-compose -f example/cluster-hostname.yaml up 

```







