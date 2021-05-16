package com.maestro.app.practice.ch2.ms.reports.clients;

import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
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

@FeignClient(name = "dept-service", url = "http://dept-service:8801", fallbackFactory = DepartmentServiceFallbackFactory.class)
public interface DepartmentService {
    @GetMapping(value = "/dept/{deptId}")
    DepartmentDto get(@PathVariable Long deptId);

    @GetMapping(value = "/dept")
    List<DepartmentDto> getAllDepartments();
}

@Slf4j
@Component
class DepartmentServiceFallbackFactory implements FallbackFactory<DepartmentService> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceFallbackFactory.class);

    @Override
    public DepartmentService create(Throwable cause) {
        LOGGER.info("fallback; reason was: {}, {}", cause.getMessage(), cause);
        return new DepartmentService() {
            @Override
            public DepartmentDto get(Long deptId) {
                return new DepartmentDto();
            }

            @Override
            public List<DepartmentDto> getAllDepartments() {
                return new ArrayList<>();
            }
        };
    }
}
