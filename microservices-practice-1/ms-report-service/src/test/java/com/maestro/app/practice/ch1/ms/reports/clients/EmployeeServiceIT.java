package com.maestro.app.practice.ch1.ms.reports.clients;

import com.maestro.app.practice.ch1.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch1.ms.reports.domain.EmployeeDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceIT {
    @Autowired
    private EmployeeService employeeService;

    private EmployeeDto emp = null;

    @BeforeAll
    public void setup() {
        final List<EmployeeDto> ret = employeeService.getAllEmployees();
        final Optional<EmployeeDto> obj = ret.stream().sorted(Comparator.comparing(EmployeeDto::getId)).findFirst();
        emp = obj.orElse(null);
    }

    @Test
    public void canViewAllEmployees() {
        final List<EmployeeDto> ret = employeeService.getAllEmployees();

        assertThat(ret.size()).isGreaterThanOrEqualTo(0);
        ret.forEach(System.out::println);
    }

    @Test
    public void canViewDeptEmployees() {
        final List<EmployeeDto> ret = employeeService.getDeptEmployees(emp.getDeptId());

        assertThat(ret.size()).isGreaterThanOrEqualTo(1);
        ret.forEach(System.out::println);
    }

        @Test
    public void canReadAPResentDept() {
        if (emp != null) {
            final EmployeeDto ret = employeeService.get(emp.getId());

            assertThat(ret).isNotNull();
            assertThat(ret).isEqualTo(emp);

            System.out.println(ret);
        } else {
            System.out.println(" no data in employee db");
        }
    }
}
