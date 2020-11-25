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
@FeignClient(value = OrderOpenFeignController.PAY_SERVICE, fallback = FallbackPaymentService.class)
public interface PaymentService {

	@PostMapping("/payment/create")
	CommonResult create(@RequestBody Payment payment);

	@GetMapping("/payment/get/{id}")
	CommonResult getById(@PathVariable("id") long id);

	@GetMapping("/payment/pay")
	CommonResult hystrixPay();

	@GetMapping("/payment/pay-timeout")
	CommonResult hystrixPayTimeout();

	@GetMapping("/pay/{id}")
	CommonResult payById(@PathVariable("id") int id);
}
