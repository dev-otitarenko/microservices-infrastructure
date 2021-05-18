package com.maestro.app.practice.ch1.ms.reports.controllers;

import com.maestro.app.practice.ch1.ms.reports.clients.DepartmentService;
import com.maestro.app.practice.ch1.ms.reports.clients.EmployeeService;
import com.maestro.app.practice.ch1.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch1.ms.reports.domain.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
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
public class StatControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @Autowired
    private JacksonTester<StatSummary> jsonSummary;
    @Autowired
    private JacksonTester<List<StatDeptSummary>> jsonListDept;

    @Test
    public void canRetrieveSummary() throws Exception {
        // given
        given(departmentService.getAllDepartments())
                .willReturn(
                        Arrays.asList(
                                new DepartmentDto(1, "MU", "Management Unit"),
                                new DepartmentDto(2, "HR", "Human Resources Unit"),
                                new DepartmentDto(3, "IT", "IT Unit")
                        )
                );
        given(employeeService.getAllEmployees())
                .willReturn(
                        Arrays.asList(
                                new EmployeeDto(1, "MU", "Dave", "Johnson", (float)3000),
                                new EmployeeDto(2, "HR", "Tom", "Fernandez", (float)4000),
                                new EmployeeDto(3, "IT", "Michael", "Cooper", (float)5000)
                        )
                );
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/summary"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSummary.write(new StatSummary(3, 3, 12000)).getJson()
        );

        System.out.println(response.getContentAsString());
    }

    @Test
    public void canRetrieveDeptSummary() throws Exception {
        final List<StatDeptSummary> res = Arrays.asList(
                new StatDeptSummary("HR", "Human Resources", 1, 4000),
                new StatDeptSummary("IT", "IT Unit", 2, 8000)
        );
        // given
        given(departmentService.getAllDepartments())
                .willReturn(
                        Arrays.asList(
                                new DepartmentDto(1, "HR", "Human Resources"),
                                new DepartmentDto(3, "IT", "IT Unit")
                        )
                );
        given(employeeService.getAllEmployees())
                .willReturn(
                        Arrays.asList(
                                new EmployeeDto(1, "IT", "Dave", "Johnson", (float)3000),
                                new EmployeeDto(2, "HR", "Tom", "Fernandez", (float)4000),
                                new EmployeeDto(3, "IT", "Michael", "Cooper", (float)5000)
                        )
                );
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/summary/dept"))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonListDept.write(res).getJson()
        );

        System.out.println(response.getContentAsString());
    }

    @Data
    //@NoArgsConstructor
    @AllArgsConstructor
    public static class StatSummary {
        private int countDepts;
        private int countEmps;
        private float salary;
    }

    @Data
  //  @NoArgsConstructor
    @AllArgsConstructor
    public static class StatDeptSummary {
        private String deptNum;
        private String name;
        private int countEmps;
        private float salary;
    }
}
