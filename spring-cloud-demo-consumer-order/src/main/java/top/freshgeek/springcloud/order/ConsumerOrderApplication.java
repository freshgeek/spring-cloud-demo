package top.freshgeek.springcloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.freshgeek.springcloud.order.controller.OrderTemplateController;
import top.freshgeek.springcloud.rule.MyRuleConfig;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description 启动类
 */
@EnableSwagger2
@EnableEurekaClient
// ribbon 使用
//@RibbonClient(name = OrderTemplateController.PAY_SERVICE,configuration = MyRuleConfig.class)
// openfeign 调用
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrderApplication.class,args);
    }
}
