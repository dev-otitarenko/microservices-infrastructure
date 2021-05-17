package com.maestro.app.practice.ch3.ms.departments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import com.maestro.app.practice.ch3.ms.departments.services.DepartmentService;
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
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Department> jsonDept;
    private JacksonTester<List<Department>> jsonListDept;

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
                get("/dept")
                        .accept(MediaType.APPLICATION_JSON))
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
