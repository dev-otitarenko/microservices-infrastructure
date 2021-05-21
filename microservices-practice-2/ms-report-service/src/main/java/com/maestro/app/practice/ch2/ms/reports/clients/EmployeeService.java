package com.maestro.app.practice.ch2.ms.reports.clients;

import com.maestro.app.practice.ch2.ms.reports.configuration.ServicesProperties;
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
    private final RestTemplate restTemplate;
    private final ServicesProperties props;

    public EmployeeService(RestTemplateBuilder restTemplateBuilder,
                           ServicesProperties props) {
        this.props = props;
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<EmployeeDto> getDeptEmployees(String deptId) {
        ResponseEntity<List<EmployeeDto>> ret = restTemplate.exchange(
                String.format("%s/dept/%s", props.getEmps(), deptId),
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
                props.getEmps() + "/emp",
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
        return restTemplate.getForObject(String.format("%s/emp/%d", props.getEmps(), id), EmployeeDto.class);
    }
}
