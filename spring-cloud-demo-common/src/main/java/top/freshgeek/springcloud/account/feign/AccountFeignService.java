package top.freshgeek.springcloud.account.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.freshgeek.springcloud.account.feign.impl.AccountFeignServiceImpl;
import top.freshgeek.springcloud.common.MiscroServiceNameConstants;
import top.freshgeek.springcloud.common.payment.CommonResult;

import java.math.BigDecimal;

/**
 * @author chen.chao
 */
@FeignClient(value = MiscroServiceNameConstants.ACCOUNT_SERVICE, fallback = AccountFeignServiceImpl.class)
public interface AccountFeignService {

    /***
     *  账号扣余额
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @PostMapping("/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException;

	@PostMapping("/account/decrease-exception")
	CommonResult decreaseException(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException;
}
