package top.freshgeek.springcloud.order.seata.service;

import org.springframework.stereotype.Service;
import top.freshgeek.springcloud.order.entity.Order;

@Service
public interface OrderService {
    void create(Order order);
}
