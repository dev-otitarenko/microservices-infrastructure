package com.maestro.app.practice.ch2.ms.reports;

import com.maestro.app.practice.ch2.ms.reports.configuration.ServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServicesProperties.class)
public class ReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}
}
