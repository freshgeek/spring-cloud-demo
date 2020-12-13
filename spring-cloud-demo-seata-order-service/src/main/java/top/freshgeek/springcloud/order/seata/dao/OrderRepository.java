package top.freshgeek.springcloud.order.seata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 修改订单状态 ，从0改到1
     *
     * @param userId
     * @param status
     */
    @Query("update Order set status = 1 where userId=:userId and status = :status")
    void update(Long userId, Integer status);
}
