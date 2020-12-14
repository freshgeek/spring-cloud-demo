package top.freshgeek.springcloud.account.seata.service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author chen.chao
 */
public interface AccountService {

	@Transactional(rollbackFor = Throwable.class)
	void decreaseException(Long userId, BigDecimal money) throws InterruptedException;

	void decrease(Long userId, BigDecimal money) throws InterruptedException;

}
