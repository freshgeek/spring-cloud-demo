package top.freshgeek.springcloud.account.seata.service;

import java.math.BigDecimal;

/**
 * @author chen.chao
 */
public interface AccountService {

	void decrease(Long userId, BigDecimal money);

}
