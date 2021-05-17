package com.maestro.app.practice.ch1.ms.employees.controllers;

import com.maestro.app.practice.ch1.ms.employees.entities.Employee;
import com.maestro.app.practice.ch1.ms.employees.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * The testing strategy #4: SpringBootTest example with a Real Web Server
 *
 * This class demonstrates how to test a controller using Spring Boot Test
 * (what makes it much closer to a Integration Test)
 *
 * When you use @SpringBootTest with WebEnvironment.RANDOM_PORT or WebEnvironment.DEFINED_PORT), you’re testing
 * with a real HTTP server. In this case, you need to use a RestTemplate or TestRestTemplate to perform the requests.
 *
 * The difference between using a random port or a defined port is just that in the first case the default port 8080
 * (or the one you override with the server.port property) won’t be used but replaced with a randomly-assigned port number.
 * This is helpful when you want to run parallel tests, to avoid port clashing. Let’s have a look at the code and
 * then describe the main characteristics.
 *
 * @author oleksii titarenko
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerSpringBootTest {
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canRetrieveAllEmployees() throws Exception {
        final List<Employee> emps = Arrays.asList(
                new Employee(1, "IT", "Tom", "Fernandez", (float)4000),
                new Employee(2, "IT", "Michael", "Cooper", (float)5000)
        );
        // given
        given(employeeService.getDeptEmployees("IT"))
                .willReturn(emps);
        // when
        ResponseEntity<List<Employee>> response = restTemplate.exchange(
                "/dept/IT",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSameSizeAs(emps);
        assertThat(response.getBody()).hasSameElementsAs(emps);
    }
}
