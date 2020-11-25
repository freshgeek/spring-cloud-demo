package top.freshgeek.springcloud.order.controller;

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
@RequestMapping("/feign/")
@RestController
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
