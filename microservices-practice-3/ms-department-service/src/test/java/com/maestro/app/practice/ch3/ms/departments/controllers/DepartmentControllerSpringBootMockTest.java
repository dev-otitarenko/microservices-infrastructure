package com.maestro.app.practice.ch3.ms.departments.controllers;

import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import com.maestro.app.practice.ch3.ms.departments.services.DepartmentService;
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
public class DepartmentControllerSpringBootMockTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentService departmentService;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<Department> jsonDept;
    @Autowired
    private JacksonTester<List<Department>> jsonListDept;

    @Test
    public void canRetrieveAllDepartments() throws Exception {
        // given
        given(departmentService.getList())
                .willReturn(
                        Arrays.asList(
                                new Department(1, "MU", "Management Unit"),
                                new Department(2, "HR", "Human Resources Unit"),
                                new Department(1, "IT", "IT Unit")
                        )
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/dept"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListDept.write(
                        Arrays.asList(
                                new Department(1, "MU", "Management Unit"),
                                new Department(2, "HR", "Human Resources Unit"),
                                new Department(1, "IT", "IT Unit")
                        )).getJson()
        );
    }

    @Test
    public void canRetrieveAPresentDept() throws Exception {
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit"));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/dept/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDept.write(
                        new Department(1, "MU", "Management Unit")
                ).getJson()
        );
    }

    @Test
    public void canCreateANewDept() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                put("/dept/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonDept.write(
                                new Department(1, "MU", "Management Unit")
                        ).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canUpdateAPresentDept() throws Exception {
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit"));
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/dept/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonDept.write(
                                new Department(1, "MU", "Management Unit - 1")
                        ).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canRemoveAPresentDept() throws Exception {
        // given
        given(departmentService.get(1L))
                .willReturn(new Department(1, "MU", "Management Unit"));
        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/dept/1")
        ).andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
