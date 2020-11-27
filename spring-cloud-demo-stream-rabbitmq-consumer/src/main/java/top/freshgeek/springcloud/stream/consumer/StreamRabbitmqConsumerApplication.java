package top.freshgeek.springcloud.stream.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chen.chao
 */
@EnableEurekaClient
@SpringBootApplication
public class StreamRabbitmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitmqConsumerApplication.class, args);
	}

}
