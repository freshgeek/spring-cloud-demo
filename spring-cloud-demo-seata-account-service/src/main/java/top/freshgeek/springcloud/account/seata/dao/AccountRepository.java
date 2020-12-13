package top.freshgeek.springcloud.account.seata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.account.entity.Account;

/**
 * @author chen.chao
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


}
