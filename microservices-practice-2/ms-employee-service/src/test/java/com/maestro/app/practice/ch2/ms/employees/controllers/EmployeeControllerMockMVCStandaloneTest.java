package com.maestro.app.practice.ch2.ms.employees.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.practice.ch2.ms.employees.entities.Employee;
import com.maestro.app.practice.ch2.ms.employees.services.EmployeeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * The testing strategy #1: Spring MockMVC example in Standalone Mode. You are not loading the Spring context
 *
 * @author oleksii titarenko
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeControllerMockMVCStandaloneTest {
    private MockMvc mvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Employee> jsonEmp;
    private JacksonTester<List<Employee>> jsonListEmp;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(employeeController)
                // .setControllerAdvice(...)
                // .addFilters(...)
                .build();
    }

    @Test
    public void canRetrieveAllEmployees() throws Exception {
        // given
        given(employeeService.getList())
                .willReturn(
                        Arrays.asList(
                                new Employee(1, "MU", "Dave", "Johnson", (float)3400),
                                new Employee(2, "HR", "Tom", "Fernandez", (float)4000),
                                new Employee(3, "IT", "Michael", "Cooper", (float)5000)
                        )
                );

        // when
        MockHttpServletResponse response = mvc.perform(get("/emp").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListEmp.write(
                        Arrays.asList(
                                new Employee(1, "MU", "Dave", "Johnson", (float)3400),
                                new Employee(2, "HR", "Tom", "Fernandez", (float)4000),
                                new Employee(3, "IT", "Michael", "Cooper", (float)5000)
                        )).getJson()
        );
    }

    @Test
    public void canRetrieveAPresentEmp() throws Exception {
        // given
        given(employeeService.get(1L))
                .willReturn(new Employee(1, "MU", "Dave", "Johnson", (float)3400));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/emp/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonEmp.write(new Employee(1, "MU", "Dave", "Johnson", (float)3400)).getJson()
        );
    }

    @Test
    public void canCreateANewEmp() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                put("/emp").contentType(MediaType.APPLICATION_JSON).content(
                        jsonEmp.write(
                                new Employee(1, "MU", "Dave", "Johnson", (float)3400)
                        ).getJson()
                )).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canUpdateAPresentEmp() throws Exception {
        // given
        given(employeeService.get(1L))
                .willReturn(new Employee(1, "MU", "Dave", "Johnson", (float)3400));
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/emp/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonEmp.write(
                                new Employee(1, "MU", "Dave", "Cruise", (float)3400)
                        ).getJson()
                )).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canRemoveAPresentEmp() throws Exception {
        // given
        given(employeeService.get(1L))
                .willReturn(new Employee(1, "MU", "Dave", "Johnson", (float)3400));
        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/emp/1")
        ).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
