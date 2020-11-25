package top.freshgeek.springcloud.payment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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


	public CommonResult histrix_pay() {
		return CommonResult.of(200, "histrix_pay : " + serverPort);
	}

	@HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	})
	public CommonResult histrix_pay_timeout() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CommonResult.of(200, "histrix_pay : " + serverPort);
	}


	public CommonResult fallbackMethod() {
		return CommonResult.of(201, "fallbackMethod :" + serverPort);
	}

}