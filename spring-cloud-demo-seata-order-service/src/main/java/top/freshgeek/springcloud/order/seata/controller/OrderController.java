package top.freshgeek.springcloud.order.seata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.order.entity.Order;
import top.freshgeek.springcloud.order.feign.OrderFeignService;
import top.freshgeek.springcloud.order.seata.service.OrderService;

import javax.annotation.Resource;

@RestController
public class OrderController implements OrderFeignService {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return CommonResult.of(200, "订单创建成功");
    }

}
