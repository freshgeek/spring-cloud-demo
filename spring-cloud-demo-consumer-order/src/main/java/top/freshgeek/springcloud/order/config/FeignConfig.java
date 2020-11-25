package top.freshgeek.springcloud.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen.chao
 */
@Configuration
public class FeignConfig {

	@Bean
	public Logger.Level feignLog() {
		return Logger.Level.FULL;
	}
}
