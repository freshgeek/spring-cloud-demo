package top.freshgeek.springcloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description 启动类
 */
@EnableAspectJAutoProxy
@EnableSwagger2
// eureka
//@EnableEurekaClient
// ribbon 使用
// openfeign 调用
@EnableFeignClients
//@EnableHystrix
// zookeeper
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerOrderApplication.class, args);
	}
}
