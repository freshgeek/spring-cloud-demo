package top.freshgeek.springcloud.stream.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chen.chao
 */
@EnableEurekaClient
@SpringBootApplication
public class StreamRabbitmqProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamRabbitmqProviderApplication.class, args);
	}

}
