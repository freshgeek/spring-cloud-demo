
# OpenFeign

Feign是声明性Web服务客户端。它使编写Web服务客户端更加容易。
要使用Feign，请创建一个接口并对其进行注释。
它具有可插入的注释支持，包括Feign注释和JAX-RS注释。
Feign还支持可插拔编码器和解码器。
Spring Cloud添加了对Spring MVC注释的支持，并支持使用HttpMessageConvertersSpring Web中默认使用的注释。Spring Cloud集成了Ribbon和Eureka以及Spring Cloud LoadBalancer，以在使用Feign时提供负载平衡的http客户端。

## 1. 作用

1. 声明式方式定义Web服务客户端；
2. 通过集成Ribbon或Eureka实现负载均衡的HTTP客户端。

> 就是我们不用再去写RestTemplate 调用微服务接口了，直接用接口调用就可以了

### 1.1 与Feign 区别
- Feign是Springcloud组件中的一个轻量级Restful的HTTP服务客户端，Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。Feign的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务
- OpenFeign是springcloud在***Feign的基础上***支持了SpringMVC的注解，如@RequestMapping等等。OpenFeign的@FeignClient可以解析SpringMVC的@RequestMapping注解下的接口，并通过***动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务***。


## 2. 怎么用
定义方法时直接复制接口的定义即可，当然还有另一种做法，就是将接口单独抽出来定义，然后在 Controller 中实现接口。

在调用的客户端中也实现了接口，从而达到接口共用的目的。我这里的做法是不共用的，即单独创建一个 API Client 的公共项目，基于约定的模式，每写一个接口就要对应写一个调用的 Client，后面打成公共的 jar，这样无论是哪个项目需要调用接口，只要引入公共的接口 SDK jar 即可，不用重新定义一遍了。

定义之后可以直接通过注入 UserRemoteClient 来调用，这对于开发人员来说就像调用本地方法一样。

## 3. 实践代码

通过订单模块调用支付模块为例，其中订单模块`spring-cloud-demo-consumer-order`是消费者，也是客户端

### 3.1 引入依赖

在客户端引入openfeign 依赖
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```

### 3.2 添加注解
这里不需要修改配置文件
```java
// openfeign 调用
@EnableFeignClients
```

### 3.3 编写业务类

我们直接把服务端`spring-cloud-demo-provider-payment` 的接口复制过来

这里我们把这些feign 接口 都放在一个包下面，也可以抽取出公共方法直接引入

- openfeign 接口
```java
package top.freshgeek.springcloud.order.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.order.controller.OrderOpenFeignController;
import top.freshgeek.springcloud.order.feign.fallback.FallbackPaymentService;

/**
 * @author chen.chao
 */
@Component
public interface PaymentService {

	@PostMapping("/payment/create")
	CommonResult create(@RequestBody Payment payment);

	@GetMapping("/payment/get/{id}")
	CommonResult getById(@PathVariable("id") long id);
 
}

```

> 注意：需要带上注解，并且保持一致，否则可能出现序列化问题
>

- controller

```java
package top.freshgeek.springcloud.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.order.feign.PaymentService;

import javax.annotation.Resource;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 10:28
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/feign/")
public class OrderOpenFeignController {

	public static final String PAY_SERVICE = "CLOUD-PAYMENT-SERVICE";

	@Resource
	private PaymentService paymentService;

	@GetMapping("payment/get/{id}")
	public CommonResult getPayment(@PathVariable("id") int id) {
		return paymentService.getById(id);
	}

	@PostMapping("payment/create")
	public CommonResult getPayment(Payment payment) {
		return paymentService.create(payment);
	}
 

}

```

这样我们就能直接想本地方法一样调用了

### 3.4 测试
1. 启动注册发现服务
2. 启动生产者
3. 启动消费者

访问 `/feign/` 下面接口

## 4. 进阶：超时控制

因为openfeign 默认会使用Ribbon 做负载均衡 ， 因此会有默认的连接超时时间和读取超时时间 

只需要配置上一节中的[Ribbon](ribbon.md) 的超时时间即可控制

## 5. 进阶： 日志记录

### 5.1 feign 日志级别

feign.Logger.Level：
- NONE：默认不显示日志
- BASIC：仅记录请求方法，URL，响应状态及执行时间
- HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息
- FULL：除了HEADERS中定义的信息外，还有请求和响应的正文及元数据

```java
package top.freshgeek.springcloud.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen.chao
 */
@Configuration
public class FeignConfig {

	@Bean
	public Logger.Level feignLog() {
		return Logger.Level.FULL;
	}
}

```

### 5.2 配置文件属性

然后指定Spring boot 对应的日志级别
```yaml
logging:
  level:
    top.freshgeek.springcloud.order.feign: debug
```


