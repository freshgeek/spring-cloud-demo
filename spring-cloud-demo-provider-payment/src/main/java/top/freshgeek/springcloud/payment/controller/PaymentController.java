package top.freshgeek.springcloud.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.payment.service.PaymentService;

import java.util.List;


/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 9:27
 * @description
 */
@Slf4j
@RequestMapping("/payment/")
@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired(required = false)
	private DiscoveryClient discoveryClient;

	@PostMapping("/create")
	public CommonResult create(@RequestBody Payment payment) {
		return paymentService.save(payment);
	}

	@GetMapping("/get/{id}")
	public CommonResult getById(@PathVariable("id") long id) {
		return paymentService.get(id);
	}

	@GetMapping("discovery")
	public Object getDiscovery() {
		List<String> services = discoveryClient.getServices();
		services.forEach(log::info);
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
		instances.forEach(s -> log.info("{}", s.toString()));
		return discoveryClient;
	}

	@GetMapping("zk")
	public Object zk() {
		return "zookeeper success ! ";
	}


	@GetMapping("pay")
	public CommonResult hystrixPay() {
		return paymentService.hystrix_pay();
	}

	@GetMapping("pay-timeout")
	public CommonResult hystrixPayTimeout() {
		return paymentService.hystrix_pay_timeout();
	}

	@GetMapping("/pay/{id}")
	public CommonResult payById(@PathVariable("id") int id) {
		return paymentService.payById(id);
	}

}
