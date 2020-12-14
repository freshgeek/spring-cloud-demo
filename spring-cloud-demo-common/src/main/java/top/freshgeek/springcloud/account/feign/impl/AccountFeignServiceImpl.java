package top.freshgeek.springcloud.account.feign.impl;

import org.springframework.stereotype.Component;
import top.freshgeek.springcloud.account.feign.AccountFeignService;
import top.freshgeek.springcloud.common.payment.CommonResult;

import java.math.BigDecimal;

@Component
public class AccountFeignServiceImpl implements AccountFeignService {

    @Override
    public CommonResult decrease(Long userId, BigDecimal money) {
        return null;
    }

    @Override
    public CommonResult decreaseException(Long userId, BigDecimal money) throws InterruptedException {
        return null;
    }

}
