package com.maestro.app.ms.practice.prometheus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestPrometheusApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestPrometheusApplication.class, args);
	}

	@GetMapping("/")
	public String welcome() {
		return "Hello from test service!!";
	}
}
