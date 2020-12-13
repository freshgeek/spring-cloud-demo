package top.freshgeek.springcloud.account.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chen.chao
 */

@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("top.freshgeek.springcloud")
public class SeataAccountApplication {


	public static void main(String[] args) {
		SpringApplication.run(SeataAccountApplication.class, args);
	}

}
