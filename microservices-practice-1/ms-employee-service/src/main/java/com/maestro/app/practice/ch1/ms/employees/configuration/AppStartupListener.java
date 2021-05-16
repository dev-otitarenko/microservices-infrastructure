package com.maestro.app.practice.ch1.ms.employees.configuration;

import com.maestro.app.practice.ch1.ms.employees.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@AllArgsConstructor
public class AppStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    private final EmployeeService empService;

    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        log.info(" ** onApplicationEvent");

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("employees.csv")) {
            empService.saveFile(inputStream);
        } catch (IOException e) {
            log.error("ApplicationListener Error", e);
        }
    }
}
