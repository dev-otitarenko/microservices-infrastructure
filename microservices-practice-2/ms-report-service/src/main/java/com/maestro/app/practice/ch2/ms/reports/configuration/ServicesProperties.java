package com.maestro.app.practice.ch2.ms.reports.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.clients")
public class ServicesProperties {
    private String emps;
    private String depts;
}
