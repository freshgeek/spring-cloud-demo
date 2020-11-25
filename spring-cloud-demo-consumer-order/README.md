
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
import org.springframework.stereotype.Component;import org.springframework.web.bind.annotation.*;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.order.controller.OrderOpenFeignController;

/**
 * @author chen.chao
 */
@Component
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

同时使用 `@Component` 加入spring
 

然后在启动类上添加注解 `@EnableFeignClients` 

## 配置超时时间

默认是1s 超出会报错

```yaml

ribbon:
  #请求处理的超时时间
  ReadTimeout: 5000

```


## ***配置 日志 debug***


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



# Hystrix 服务消费者降级

首先,在启动类上面添加 `@EnableHystrix`


第二步, 在业务代码中添加兜底方法

```java


	@GetMapping("/payment/pay-timeout")
	@HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "300")
	})
	CommonResult hystrixPayTimeout() {
		return paymentService.hystrixPayTimeout();
	}

	public CommonResult fallbackMethod() {
		return CommonResult.of(202, "fallbackMethod :" + "我是80 我为自己代言");
	}


```


第三步:在属性文件中开启 hystrix 
```yaml
feign:
  hystrix:
    enabled: true
```


## 消除服务降级代码耦合

可以使用两种策略:
1. DefaultProperties 全局兜底
2. **@FeignClient(fallback = FallbackPaymentService.class) 推荐**

### 第一种 在需要降级业务类上直接添加 全局兜底 , 如果有单独兜底还是走单独兜底方法

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
@DefaultProperties(defaultFallback = "globalFallBackTimeOut")
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


	@GetMapping("/payment/pay")
	CommonResult hystrixPay() {
		return paymentService.hystrixPay();
	}

	@GetMapping("/payment/pay-timeout")
	@HystrixCommand
	CommonResult hystrixPayTimeout() {
		return paymentService.hystrixPayTimeout();
	}

	public CommonResult globalFallBackTimeOut() {
		return CommonResult.of(9001, "全局服务超时,请稍后再试");
	}

}
```

在 全局类上  添加 `@DefaultProperties(defaultFallback = "globalFallBackTimeOut")` 
然后在类中实现这个方法 , 通常可以通过状态码配合前端做出通用的交互提示 , 如`服务正忙`等 

再抽象可以统一实现类,将通用兜底放抽象类中 然后实现抽象类

### 2. 直接实现feign 接口 进行兜底 (配合aop 切面 实现全局和特例共存)

1. 首先实现 feign 接口 , 并且为了规范 放在feign 下的fallback 包中

```java

package top.freshgeek.springcloud.order.feign.fallback;

import org.springframework.stereotype.Component;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.order.feign.PaymentService;

/**
 * @author chen.chao
 */
@Component
public class FallbackPaymentService implements PaymentService {

	@Override
	public CommonResult create(Payment payment) {
		return null;
	}

	@Override
	public CommonResult getById(long id) {
		return null;
	}

	@Override
	public CommonResult hystrixPay() {
		return CommonResult.of(209, "活动已关闭,请下次再来");
	}

	@Override
	public CommonResult hystrixPayTimeout() {
		return null;
	}
}

```


2. 针对这个包进行aop 切面 代理

```java

package top.freshgeek.springcloud.order.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import top.freshgeek.springcloud.common.payment.CommonResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen.chao
 */
@Aspect
@Configuration
public class FallbackAspect {


	/**
	 * 这里我们使用注解的形式
	 * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
	 * 切点表达式:   execution(...)
	 */
	@Pointcut("execution( * top.freshgeek.springcloud.order.feign.fallback..*(..))")
	public void logPointCut() {
	}

	/**
	 * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
	 *
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object proceed = point.proceed();
		return proceed == null ? CommonResult.of(9007, "全局兜底降级,请稍后再试") : proceed;
	}


}

```

3. 最后开启 `@EnableAspectJAutoProxy` 代理

即可实现共存,如果需要特例处理,只需要在fallback 实现类中实现即可
