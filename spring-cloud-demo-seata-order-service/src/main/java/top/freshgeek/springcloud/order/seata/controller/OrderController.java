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
    public CommonResult create(Order order) throws InterruptedException {
        // 正常创建
        orderService.create(order);
        return CommonResult.of(200, "订单创建成功");
    }
    @GetMapping("/order/create-excetion")
    public CommonResult createException(Order order) throws InterruptedException {
        // 异常不回滚状态
        orderService.createUnroll(order);
        return CommonResult.of(200, "订单创建成功");
    }
    @GetMapping("/order/create-excetion-roll")
    public CommonResult createExceptionRoll(Order order) throws InterruptedException {
        // 异常回滚
        orderService.createExceptionRoll(order);
        return CommonResult.of(200, "订单创建成功");
    }

}
