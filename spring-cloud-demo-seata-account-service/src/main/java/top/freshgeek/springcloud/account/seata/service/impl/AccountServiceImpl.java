package top.freshgeek.springcloud.account.seata.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.freshgeek.springcloud.account.entity.Account;
import top.freshgeek.springcloud.account.seata.dao.AccountRepository;
import top.freshgeek.springcloud.account.seata.service.AccountService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void decrease(Long userId, BigDecimal money) {
        log.info("------->account-service中扣减账户余额开始");
        //模拟超时异常，全局事务回滚
        //暂停几秒钟线程
//		TimeUnit.SECONDS.sleep(10);
        Optional<Account> id = accountRepository.findById(userId);
        Assert.isTrue(id.isPresent(), "用户不存在");
        Account account = id.get();
        account.getTotal().subtract(money);
        Assert.isTrue(BigDecimal.ZERO.compareTo(account.getTotal()) < 0, "扣款失败");
        accountRepository.save(account);
        log.info("------->account-service中扣减账户余额结束");
    }
}
