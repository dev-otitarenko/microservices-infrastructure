package com.maestro.app.practice.ch2.ms.reports.clients;

import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch2.ms.reports.domain.EmployeeDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeService {
    private final String EMP_PATH_V1 = "http://emp-service:8802/emp";
    private final String DEPT_PATH_V1 = "http://emp-service:8802/dept";
    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<EmployeeDto> getDeptEmployees(String deptId) {
        ResponseEntity<List<EmployeeDto>> ret = restTemplate.exchange(
                String.format("%s/%s", DEPT_PATH_V1, deptId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        if (ret.hasBody()){
            return ret.getBody();
        }
        return new ArrayList<>();
    }

    @GetMapping(value = "/emp")
    public List<EmployeeDto> getAllEmployees() {
        ResponseEntity<List<EmployeeDto>> ret = restTemplate.exchange(
                EMP_PATH_V1,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        if (ret.hasBody()){
            return ret.getBody();
        }
        return new ArrayList<>();
    }


    @GetMapping(value = "/emp/{id}")
    public EmployeeDto get(@PathVariable Long id) {
        return restTemplate.getForObject(String.format("%s/%d", EMP_PATH_V1, id), EmployeeDto.class);
    }
}
