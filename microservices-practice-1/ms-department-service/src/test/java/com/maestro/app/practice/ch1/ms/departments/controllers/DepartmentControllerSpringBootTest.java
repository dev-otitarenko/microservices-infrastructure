package com.maestro.app.practice.ch1.ms.departments.controllers;

import com.maestro.app.practice.ch1.ms.departments.entities.Department;
import com.maestro.app.practice.ch1.ms.departments.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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
    private DepartmentService departmentService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void canRetrieveAllDepartments() {
        List<Department> listDepts = Arrays.asList(
                new Department(1, "MU", "Management Unit"),
                new Department(2, "HR", "Human Resources Unit"),
                new Department(1, "IT", "IT Unit")
        );
        // given
        given(departmentService.getList()).willReturn(listDepts);
        // when
        ResponseEntity<List<Department>> response = restTemplate.exchange(
                "/dept",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSameSizeAs(listDepts);
        assertThat(response.getBody()).hasSameElementsAs(listDepts);
    }

    @Test
    public void canRetrieveAPresentDept() {
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit"));
        // when
        final ResponseEntity<Department> response = restTemplate.getForEntity("/dept/1", Department.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new Department(1, "MU", "Management Unit"));
    }

    @Test
    public void canCreateANewDept() {
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<Department> request = new HttpEntity<>(new Department(1, "MU", "Management Unit"), headers);
        // when
        final ResponseEntity<Void> response = restTemplate.exchange("/dept", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void canUpdateAPresentDept() {
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<Department> request = new HttpEntity<>(new Department(1, "MU", "Management Unit"), headers);
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit - 1"));
        // when
        final ResponseEntity<Void> response = restTemplate.exchange("/dept/1", HttpMethod.POST, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void canRemoveAPresentDept() {
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<Department> request = new HttpEntity<>(headers);
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit"));
        // when
        final ResponseEntity<Void> response = restTemplate.exchange("/dept/1", HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }
}
