package top.freshgeek.springcloud.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.payment.entity.Payment;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 9:27
 * @description
 */
@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
}