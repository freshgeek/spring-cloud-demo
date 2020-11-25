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
