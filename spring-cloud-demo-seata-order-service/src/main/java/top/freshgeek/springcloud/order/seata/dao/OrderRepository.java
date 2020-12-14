package top.freshgeek.springcloud.order.seata.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.freshgeek.springcloud.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
