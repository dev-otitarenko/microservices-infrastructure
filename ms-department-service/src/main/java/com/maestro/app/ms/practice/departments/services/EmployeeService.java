package com.maestro.app.ms.practice.departments.services;

import com.maestro.app.ms.practice.departments.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "emp-service", url = "http://emp-service:8802", fallbackFactory = EmployeeServiceFallbackFactory.class)
public interface EmployeeService {
    @GetMapping(value = "/dept/{deptId}")
    List<Employee> getDeptEmployees(@PathVariable String deptId);
}

@Slf4j
@Component
class EmployeeServiceFallbackFactory implements FallbackFactory<EmployeeService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceFallbackFactory.class);

    @Override
    public EmployeeService create(Throwable cause) {
        LOGGER.info("fallback; reason was: {}, {}", cause.getMessage(), cause);
        return new EmployeeService() {
            @Override
            public List<Employee> getDeptEmployees(String deptId) {
                return new ArrayList<>();
            }
        };
    }
}