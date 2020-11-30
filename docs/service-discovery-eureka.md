
# Eureka 服务 

## Eureka server
 `spring-cloud-demo-eureka-server` 工程提供 eureka server ，分三步

### 1. 引入pom文件

```xml
        <!--    这个是重点 ， -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
        </dependency>

        <!--热部署-->
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

### 2. 配置 application.yml

```yaml
server:
  port: 8761


eureka:
  instance:
    hostname: localhost
  client:
    # 不注册自己
    register-with-eureka: false
    # 维护 不检索
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false
    # 清理无效节点的时间间隔，默认60000毫秒，即60秒
    eviction-interval-timer-in-ms: 10
spring:
  freemarker:
    template-loader-path: classpath:/templates/
    prefer-file-system-access: false
```

### 3. 写启动类 EurekaServerApplication

> @EnableEurekaServer 重点就是这个

```java
package top.freshgeek.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 11:31
 * @description
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}

```


最后就可以启动了，可以在控制台看到很多日志打印，如下：

```text
2020-11-30 20:29:19.185  INFO 33572 --- [a-EvictionTimer] c.n.e.registry.AbstractInstanceRegistry  : Running the evict task with compensationTime 0ms
2020-11-30 20:29:19.196  INFO 33572 --- [a-EvictionTimer] c.n.e.registry.AbstractInstanceRegistry  : Running the evict task with compensationTime 1ms
2020-11-30 20:29:19.207  INFO 33572 --- [a-EvictionTimer] c.n.e.registry.AbstractInstanceRegistry  : Running the evict task with compensationTime 0ms
2020-11-30 20:29:19.218  INFO 33572 --- [a-EvictionTimer] c.n.e.registry.AbstractInstanceRegistry  : Running the evict task with compensationTime 1ms
```

这是正常的检测，在检测不正常的节点剔除，如果不想看到可以调整日志级别屏蔽，如：
```yaml
logging:
  level:
    com:
      netflix:
        eureka:
          registry: warn
```

## Eureka 微服务的消费者生产者配置使用

那么单机服务就搭建成功了，启动微服务的消费者-生产者测试：
- spring-cloud-demo-provider-payment （提供支付服务，生产者）
- spring-cloud-demo-consumer-order （订单调用支付服务，消费者）

同样分为三步：

### 1. 引入pom 

> 需要连接eureka server 那么就要引用 eureka client 的 依赖 
>
> 服务发现使用一个即可，其他几个后面文章介绍
>

```xml
 <!--eureka 注册中心   选一-->
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                </dependency>
        <!--eureka 注册中心   选一-->
```



### 2. 修改applicationyml

- spring-cloud-demo-provider-payment 生产者

```yaml

server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    # 当前数据源操作类型
    type: com.alibaba.druid.pool.DruidDataSource
    # mysql驱动类
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://data.keepon.site:3306/springcloud-payment?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
    username: springcloud
    password: springcloud
    # 这里用了jpa 省得写dao了
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    # 注册进eureka
    register-with-eureka: true
    # 要不要去注册中心获取其他服务的地址
    fetch-registry: true
    # 这里需要配置一下eureka 服务器的 host域名
    service-url:
      defaultZone: http://eureka01:8761/eureka/

```

- spring-cloud-demo-consumer-order 消费者

```yaml

server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    fetch-registry: true
    service-url:
      defaultZone: http://eureka01:8761/eureka/
spring:
  application:
    name: cloud-consumer-order

```

> 这里需要把eureka server 启动的地址和端口对应上
> 如果没有域名可以自己在host 里面添加一条

其实两个也都差不多，主要就是两个配置参数
 - `eureka.client.register-with-eureka` 是否注册入eureka ，别人需要调用开
 - `eureka.client.fetch-registry` 是否拉取服务列表，需要调用别人开

### 3.添加主启动类

- spring-cloud-demo-provider-payment

```java

package top.freshgeek.springcloud.payment;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description 启动类
 */
@EnableSwagger2
// 在eureka 环境下才加@EnableEurekaClient
@EnableEurekaClient
@SpringBootApplication
@EntityScan("top.freshgeek.springcloud.entity")
public class ProviderPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderPaymentApplication.class, args);
	}
}


```

- spring-cloud-demo-consumer-order

```java
package top.freshgeek.springcloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description 启动类
 */
@EnableSwagger2
// eureka
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerOrderApplication.class, args);
	}
}

```

### 4. 编写业务类

- spring-cloud-demo-provider-payment （生产者就不解释了，curd）
- spring-cloud-demo-consumer-order 消费者，怎么调用

我们可以在 `http://eureka01:8761/` 看到已经出现了 `CLOUD-PAYMENT-SERVICE` 

因此我们可以使用一个配置，然后使用服务名`CLOUD-PAYMENT-SERVICE` 调用它

RestTemplate 配置

```java
package top.freshgeek.springcloud.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 10:29
 * @description
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

```

```java
package top.freshgeek.springcloud.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;

import javax.annotation.Resource;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 10:28
 * @description
 *
 * 使用rest template + ribbon 结合 使用
 *
 */
@Slf4j
@RequestMapping("/consumer/")
@RestController
public class OrderTemplateController {

//    static final String PAYMENT = "http://localhost:8001";
    public static final String PAY_SERVICE = "CLOUD-PAYMENT-SERVICE";
    static final String PAYMENT = "http://"+PAY_SERVICE;
    // zk 的是小写
//    static final String PAYMENT = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;


    @GetMapping("payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") int id) {
        return restTemplate.getForObject(PAYMENT + "/payment/get/" + id, CommonResult.class);
    }

    @PostMapping("payment/create")
    public CommonResult getPayment(Payment payment) {
        return restTemplate.postForObject(PAYMENT + "/payment/create",
                payment, CommonResult.class);
    }

}

```


这样我们就完成了微服务的注册和发现了

