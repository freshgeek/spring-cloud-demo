package top.freshgeek.springcloud.account.seata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.account.entity.Account;

import java.math.BigDecimal;

/**
 * @author chen.chao
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("   UPDATE Account SET residue = residue - #{money},used = used + #{money} WHERE id = #{id}")
	boolean updateBalanceByIdAndResidue(Long id, BigDecimal money);

}
