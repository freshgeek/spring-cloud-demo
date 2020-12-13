package top.freshgeek.springcloud.order.seata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.freshgeek.springcloud.account.feign.AccountFeignService;
import top.freshgeek.springcloud.order.entity.Order;
import top.freshgeek.springcloud.order.seata.dao.OrderRepository;
import top.freshgeek.springcloud.order.seata.service.OrderService;
import top.freshgeek.springcloud.storage.entity.feign.StorageFeignService;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderDao;
    @Resource
    private StorageFeignService storageFeignService;
    @Resource
    private AccountFeignService accountFeignService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：下订单->扣库存->减余额->改状态
     */
    @Override
    @Transactional
    // @GlobalTransactional(name = "seata-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----->开始新建订单");
        //1 新建订单
        order.setStatus(0);
        orderDao.save(order);
        //2 扣减库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageFeignService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");

        //3 扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountFeignService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //4 修改订单状态，从零到1,1代表已经完成
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");

    }
}
