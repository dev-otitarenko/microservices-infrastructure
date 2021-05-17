package com.maestro.app.practice.ch3.ms.departments.controllers;

import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import com.maestro.app.practice.ch3.ms.departments.services.DepartmentService;
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
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerMockMvcWithContextTest {
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
