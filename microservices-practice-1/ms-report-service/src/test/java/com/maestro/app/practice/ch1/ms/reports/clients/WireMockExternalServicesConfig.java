package com.maestro.app.practice.ch1.ms.reports.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@TestConfiguration
public class WireMockExternalServicesConfig {
    @Autowired
    private WireMockServer wireMockServer;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksService() {
        return new WireMockServer(wireMockConfig().port(8899).extensions(new StubExternalServiceResponse()));
    }
}
