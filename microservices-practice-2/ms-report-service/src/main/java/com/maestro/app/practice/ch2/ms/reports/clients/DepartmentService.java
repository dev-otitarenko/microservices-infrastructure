package com.maestro.app.practice.ch2.ms.reports.clients;

import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

// @ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class DepartmentService {
    private final String DEPT_PATH_V1 = "http://dept-service:8801/dept";
    private final RestTemplate restTemplate;

    public DepartmentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<DepartmentDto> getAllDepartments() {
        ResponseEntity<List<DepartmentDto>> ret = restTemplate.exchange(
                DEPT_PATH_V1,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        if (ret.hasBody()){
            return ret.getBody();
        }
        return new ArrayList<>();
    }

    public DepartmentDto get(@PathVariable Long deptId) {
        return restTemplate.getForObject(String.format("%s/%d", DEPT_PATH_V1, deptId), DepartmentDto.class);
    }
}
