package top.freshgeek.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/29 11:31
 * @description
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
