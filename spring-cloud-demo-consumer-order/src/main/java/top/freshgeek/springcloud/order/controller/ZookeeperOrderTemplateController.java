package top.freshgeek.springcloud.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.rule.MyRuleConfig;

import javax.annotation.Resource;


/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 10:28
 * @description 使用rest template + ribbon 结合 使用
 */
@Slf4j
@RequestMapping("/zookeeper/")
@RibbonClient(name = ZookeeperOrderTemplateController.PAY_SERVICE, configuration = MyRuleConfig.class)
@RestController
public class ZookeeperOrderTemplateController {

	// zk 的是小写
	public static final String PAY_SERVICE = "cloud-payment-service";
	static final String PAYMENT = "http://" + PAY_SERVICE;
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
