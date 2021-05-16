package com.maestro.app.practice.ch3.ms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		//@formatter:off
		return builder.routes()
				.route("route-dpt-service", r -> r.path("/depts/**")
						.filters(f -> f.rewritePath("/depts(?<segment>/?.*)", "$\\{segment}"))
						.uri("http://dept-service:8801"))
				.route("route-emp-service", r -> r.path("/emps/**")
						.filters(f -> f.rewritePath("/emps(?<segment>/?.*)", "$\\{segment}"))
						.uri("http://emp-service:8802"))
				.route("route-stat-service", r -> r.path("/stat/**")
						.filters(f -> f.rewritePath("/stat(?<segment>/?.*)", "$\\{segment}"))
						.uri("http://report-service:8803"))
//				.route("path_route", r -> r.path("/get")
//						.uri("http://httpbin.org"))
//				.route("host_route", r -> r.host("*.myhost.org")
//						.uri("http://httpbin.org"))
//				.route("rewrite_route", r -> r.host("*.rewrite.org")
//						.filters(f -> f.rewritePath("/foo/(?<segment>.*)",
//								"/${segment}"))
//						.uri("http://httpbin.org"))
//				.route("limit_route", r -> r
//						.host("*.limited.org").and().path("/anything/**")
//						.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//						.uri("http://httpbin.org"))
//				.route("websocket_route", r -> r.path("/echo")
//						.uri("ws://localhost:9000"))
				.build();
		//@formatter:on
	}
}
