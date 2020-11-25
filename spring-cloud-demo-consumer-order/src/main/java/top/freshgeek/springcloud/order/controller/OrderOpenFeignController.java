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
//@DefaultProperties(defaultFallback = "globalFallBackTimeOut")
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
//	@HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
//	})
//	@HystrixCommand
	CommonResult hystrixPayTimeout() {
		return paymentService.hystrixPayTimeout();
	}

	public CommonResult fallbackMethod() {
		return CommonResult.of(202, "fallbackMethod :" + "我是80 我为自己代言");
	}

	public CommonResult globalFallBackTimeOut() {
		return CommonResult.of(9001, "全局服务超时,请稍后再试");
	}

}
