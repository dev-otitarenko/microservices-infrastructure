package com.maestro.app.ms.practice.prometheus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class TestPrometheusApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestPrometheusApplication.class, args);
	}

	@GetMapping("/")
	public String welcome() {
		log.info("Welcome API call");
		return "Hello from test service!!";
	}
}
