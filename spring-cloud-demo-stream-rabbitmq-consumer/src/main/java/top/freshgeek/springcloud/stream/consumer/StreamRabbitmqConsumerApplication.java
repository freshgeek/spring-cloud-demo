package top.freshgeek.springcloud.stream.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chen.chao
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StreamRabbitmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitmqConsumerApplication.class, args);
	}

}
