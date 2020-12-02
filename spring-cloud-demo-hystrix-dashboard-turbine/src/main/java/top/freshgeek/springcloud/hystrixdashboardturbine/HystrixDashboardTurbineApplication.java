package top.freshgeek.springcloud.hystrixdashboardturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author chen.chao
 */
@EnableTurbine
@SpringCloudApplication
public class HystrixDashboardTurbineApplication {
	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardTurbineApplication.class, args);
	}

}
