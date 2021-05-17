package com.maestro.app.practice.ch1.ms.employees.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.practice.ch1.ms.employees.entities.Employee;
import com.maestro.app.practice.ch1.ms.employees.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * The testing strategy #1: Spring MockMVC example in Standalone Mode. You are not loading the Spring context
 *
 * @author oleksii titarenko
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DepartmentControllerMockMVCStandaloneTest {
    private MockMvc mvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentController departmentController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<List<Employee>> jsonListEmp;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(departmentController)
                // .setControllerAdvice(...)
                // .addFilters(...)
                .build();
    }

    @Test
    public void canRetrieveAllEmployees() throws Exception {
        // given
        given(employeeService.getDeptEmployees("IT"))
                .willReturn(
                        Arrays.asList(
                                new Employee(1, "IT", "Tom", "Fernandez", (float)4000),
                                new Employee(2, "IT", "Michael", "Cooper", (float)5000)
                        )
                );

        // when
        MockHttpServletResponse response = mvc.perform(get("/dept/IT").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListEmp.write(
                        Arrays.asList(
                                new Employee(1, "IT", "Tom", "Fernandez", (float)4000),
                                new Employee(2, "IT", "Michael", "Cooper", (float)5000)
                        )).getJson()
        );
    }
}
