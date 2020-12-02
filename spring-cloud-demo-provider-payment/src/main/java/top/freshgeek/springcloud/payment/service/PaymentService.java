package top.freshgeek.springcloud.payment.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.entity.payment.Payment;
import top.freshgeek.springcloud.payment.dao.PaymentJpaRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 9:27
 * @description
 */
@Slf4j
@Service
public class PaymentService {

	@Autowired
	private PaymentJpaRepository paymentJpaRepository;

	@Value("${server.port}")
	private Integer serverPort;

	public CommonResult get(long arg) {
		Optional<Payment> byId = paymentJpaRepository.findById(arg);
		log.debug("查询[{}]到数据[{}]", arg, byId);
		return !byId.isPresent() ? CommonResult.of(501, "没有查询到结果") : CommonResult.ofSuccess(byId.get());
	}

	public CommonResult save(Payment arg) {
		Payment save = paymentJpaRepository.save(arg);
		log.debug("保存数据[{}]", save);
		return CommonResult.ofSuccess(save);
	}


	public CommonResult hystrix_pay() {
		return CommonResult.of(200, "hystrix_pay : " + serverPort);
	}
	// --------------------- 服务降级 -----------------------------------------

	@HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public CommonResult hystrix_pay_timeout() {
		try {
			int i = 10 / 0;
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CommonResult.of(200, "hystrix_pay_timeout : " + serverPort);
	}


	public CommonResult fallbackMethod() {
		return CommonResult.of(201, "fallbackMethod :" + serverPort);
	}
	// --------------------- 服务降级 -----------------------------------------

	// --------------------- 服务熔断 -----------------------------------------

	@HystrixCommand(fallbackMethod = "payByIdFallback", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
	})
	public CommonResult payById(Integer id) {
		Assert.isTrue(id > 0, "金额必须大于零");
		return CommonResult.of(200, "流水号:" + IdUtil.fastUUID() + "id :" + id);
	}

	public CommonResult payByIdFallback(Integer id) {
		return CommonResult.of(200, "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id);
	}
	// --------------------- 服务熔断 -----------------------------------------

}