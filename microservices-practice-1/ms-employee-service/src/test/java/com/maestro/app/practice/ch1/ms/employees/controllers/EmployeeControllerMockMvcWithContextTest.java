package com.maestro.app.practice.ch1.ms.employees.controllers;

import com.maestro.app.practice.ch1.ms.employees.entities.Employee;
import com.maestro.app.practice.ch1.ms.employees.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
 * The testing strategy #2: Spring MockMVC with WebApplicationContext
 *
 * The second strategy we can use to write Unit Tests for a Controller also involves MockMVC, but in this case
 * we use a Spring’s WebApplicationContext. Since we’re still using an inside-server strategy,
 * there is no web server deployed in this case though.
 *
 * @author oleksii titarenko
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerMockMvcWithContextTest {
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
