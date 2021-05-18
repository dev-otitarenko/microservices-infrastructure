package com.maestro.app.practice.ch1.ms.reports.clients;

import com.maestro.app.practice.ch1.ms.reports.domain.EmployeeDto;
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

@FeignClient(name = "emp-service", url = "${app.services.emp-service}", fallbackFactory = EmployeeServiceFallbackFactory.class)
public interface EmployeeService {
    @GetMapping(value = "/dept/{deptId}")
    List<EmployeeDto> getDeptEmployees(@PathVariable String deptId);

    @GetMapping(value = "/emp")
    List<EmployeeDto> getAllEmployees();


    @GetMapping(value = "/emp/{id}")
    EmployeeDto get(@PathVariable Long id);
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
            public List<EmployeeDto> getDeptEmployees(String deptId) {
                return new ArrayList<>();
            }

            @Override
            public List<EmployeeDto> getAllEmployees() {
                return new ArrayList<>();
            }

            @Override
            public EmployeeDto get(Long deptId) {
                return new EmployeeDto();
            }
        };
    }
}
