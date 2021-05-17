package com.maestro.app.practice.ch3.ms.employees.controllers;

import com.maestro.app.practice.ch3.ms.employees.entities.Employee;
import com.maestro.app.practice.ch3.ms.employees.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * The testing strategy #3: SpringBootTest with a MOCK WebEnvironment value
 * This class demonstrates how to test a controller using Spring Boot Test
 * with a MOCK web environment, which makes it similar to just using @WebMvcTest
 *
 * If you use @SpringBootTest without parameters, or with webEnvironment = WebEnvironment.MOCK,
 * you don’t load a real HTTP server. Does it sound familiar? It’s a similar approach to the strategy 2
 * (MockMVC with an application context). When we use this configuration, we’re still coding an inside-server test.
 *
 * In this setup, we can’t use a standard RestTemplate since we don’t have any web server. We need to keep using MockMVC,
 * which now is getting configured thanks to the extra annotation @AutoconfigureMockMVC. This is the trickiest approach
 * between all the available ones in my opinion, and I personally discourage using it.
 * Instead, it’s better to choose the Strategy 2 with MockMVC and the context loaded for a specific controller.
 * You’ll be more in control of what you’re testing.
 *
 * @author oleksii titarenko
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerSpringBootMockTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<Employee> jsonEmp;
    @Autowired
    private JacksonTester<List<Employee>> jsonListEmp;

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
