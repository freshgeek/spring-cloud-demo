package top.freshgeek.springcloud.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description 启动类
 */
@EnableSwagger2
// 在eureka 环境下才加
// @EnableEurekaClient
// zookeeper 单独用这个即可
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("top.freshgeek.springcloud.entity")
public class ProviderPaymentApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProviderPaymentApplication.class,args);
    }
}
