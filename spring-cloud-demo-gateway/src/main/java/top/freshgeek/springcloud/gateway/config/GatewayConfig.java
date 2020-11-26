package top.freshgeek.springcloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen.chao
 */
@Configuration
public class GatewayConfig {


	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
		RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
		// 对应
		// spring.cloud.gateway.routes
		// 一个id   一个path - 一个uri
		routes.route("path_route_baidu", r -> r.path("/guonei")
				.uri("http://news.baidu.com/guonei"))
		;

		return routes.build();
	}
}
