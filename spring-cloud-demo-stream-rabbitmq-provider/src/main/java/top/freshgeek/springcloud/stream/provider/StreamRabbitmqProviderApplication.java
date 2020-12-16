package top.freshgeek.springcloud.stream.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chen.chao
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StreamRabbitmqProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitmqProviderApplication.class, args);
	}

}
