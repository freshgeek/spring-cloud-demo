
首先引入pom 

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```

然后 新建接口 , 这里的接口需要与微服务 生产者端的接口 路径 参数 注解 一致 

```java
package top.freshgeek.springcloud.order.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.order.controller.OrderOpenFeignController;

/**
 * @author chen.chao
 */
@RestController("/payment")
@FeignClient(value = OrderOpenFeignController.PAY_SERVICE)
public interface PaymentService {

	@PostMapping("/payment/create")
	CommonResult create(@RequestBody Payment payment);

	@GetMapping("/payment/get/{id}")
	CommonResult getById(@PathVariable("id") long id);

	@GetMapping("/payment/pay")
	CommonResult histrixPay();

	@GetMapping("/payment/pay-timeout")
	CommonResult histrixPayTimeout();
}
```

其中需要指定微服务的应用名 ,  可以把所有微服务名定义常量放置在公共类中

接口也可以抽取成接口模块 这样就可以避免多次书写 

同时可以使用 `@RestController("/payment")` 

> 注意 : 这里里面的值没有生效 , 只能在接口中写全部的路径


然后在启动类上添加注解 `@EnableFeignClients` 



***配置 日志 debug***


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