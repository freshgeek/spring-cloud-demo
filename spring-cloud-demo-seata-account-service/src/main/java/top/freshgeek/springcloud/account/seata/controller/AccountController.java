package top.freshgeek.springcloud.account.seata.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.freshgeek.springcloud.account.feign.AccountFeignService;
import top.freshgeek.springcloud.account.seata.service.AccountService;
import top.freshgeek.springcloud.common.dto.CommonResult;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class AccountController implements AccountFeignService {

    @Resource
    private AccountService accountService;

    /**
     * 扣减账户余额
     *
     * @return
     */
    @Override
    @PostMapping("/account/decrease")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException {
        accountService.decrease(userId, money);
        return CommonResult.of(200, "扣减账户余额成功！");
    }

    /**
     * 扣减账户余额
     *
     * @return
     */
    @Override
    @PostMapping("/account/decrease-exception")
    public CommonResult decreaseException(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException {
        accountService.decreaseException(userId, money);
        return CommonResult.of(200, "扣减账户余额成功！");
    }

}