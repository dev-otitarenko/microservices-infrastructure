package com.maestro.app.practice.ch2.ms.reports.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maestro.app.practice.ch2.ms.reports.clients.DepartmentService;
import com.maestro.app.practice.ch2.ms.reports.clients.EmployeeService;
import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch2.ms.reports.domain.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentDetailControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;

    @Test
    public void canRetrieveDeptDetail() throws Exception {
        final StatSummary res = new StatSummary(
                "IT",
                "IT Unit",
                Arrays.asList(
                        new StatEmp("Dave", "Johnson", 3000),
                        new StatEmp("Tom", "Fernandez", 4000),
                        new StatEmp("Michael", "Cooper", 5000)
                )
        );
        // given
        given(departmentService.get(1L))
                .willReturn(new DepartmentDto(1, "IT", "IT Unit"));
        given(employeeService.getDeptEmployees("IT"))
                .willReturn(
                        Arrays.asList(
                                new EmployeeDto(1, "IT", "Dave", "Johnson", (float) 3000),
                                new EmployeeDto(2, "IT", "Tom", "Fernandez", (float) 4000),
                                new EmployeeDto(3, "IT", "Michael", "Cooper", (float) 5000)
                        )
                );
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/dept/1/details"))
                .andReturn().getResponse();
        //
        ObjectMapper mapper = new ObjectMapper();
        final StatSummary obj = mapper.readValue(response.getContentAsString(), StatSummary.class);
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(obj).isNotNull();
        assertThat(obj).isEqualTo(res);

        System.out.println(response.getContentAsString());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatSummary {
        private String deptNum;
        private String name;
        private List<StatEmp> employees;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class StatEmp {
        private String firstName;
        private String familyName;
        private float salary;
    }
}
