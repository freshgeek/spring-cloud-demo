package top.freshgeek.springcloud.order.feign.fallback;

import org.springframework.stereotype.Component;
import top.freshgeek.springcloud.common.dto.CommonResult;
import top.freshgeek.springcloud.payment.entity.Payment;
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

	@Override
	public CommonResult payById(int id) {
		return null;
	}
}
