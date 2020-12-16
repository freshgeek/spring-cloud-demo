package top.freshgeek.springcloud.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.freshgeek.springcloud.common.MiscroServiceNameConstants;
import top.freshgeek.springcloud.common.dto.CommonResult;
import top.freshgeek.springcloud.payment.entity.Payment;
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
@RibbonClient(name = MiscroServiceNameConstants.CLOUD_PAYMENT_SERVICE, configuration = MyRuleConfig.class)
@RestController
public class ZookeeperOrderTemplateController {

	// zk 的是小写
	static final String PAYMENT = "http://" + MiscroServiceNameConstants.CLOUD_PAYMENT_SERVICE;
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
