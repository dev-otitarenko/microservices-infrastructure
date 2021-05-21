package com.maestro.app.practice.ch2.ms.reports.clients;

import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
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
public class DepartmentServiceIT {
    @Autowired
    private DepartmentService departmentService;

    private DepartmentDto dpt = null;

    @BeforeAll
    public void setup() {
        final List<DepartmentDto> ret = departmentService.getAllDepartments();
        final Optional<DepartmentDto> obj = ret.stream().sorted(Comparator.comparing(DepartmentDto::getId)).findFirst();
        dpt = obj.orElse(null);
    }

    @Test
    public void canViewAllDepartments() {
        final List<DepartmentDto> ret = departmentService.getAllDepartments();

        assertThat(ret.size()).isGreaterThanOrEqualTo(0);
        ret.forEach(System.out::println);
    }

    @Test
    public void canReadAPResentDept() {
        if (dpt != null) {
            final DepartmentDto ret = departmentService.get(dpt.getId());

            assertThat(ret).isNotNull();
            assertThat(ret).isEqualTo(dpt);

            System.out.println(ret);
        } else {
            System.out.println(" no data in department db");
        }
    }
}
