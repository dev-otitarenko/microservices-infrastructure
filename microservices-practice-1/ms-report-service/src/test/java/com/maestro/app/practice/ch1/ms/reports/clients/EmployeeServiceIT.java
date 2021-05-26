package com.maestro.app.practice.ch1.ms.reports.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maestro.app.practice.ch1.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch1.ms.reports.domain.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockExternalServicesConfig.class })
public class EmployeeServiceIT {
    @Autowired
    private WireMockServer mockService;

    @Autowired
    private EmployeeService empService;

    @Test
    public void canViewAllEmployees() {
        final List<EmployeeDto> lst = Arrays.asList(
                new EmployeeDto(1, "MU", "Dave", "Johnson", (float)3400),
                new EmployeeDto(2, "HR", "Tom", "Fernandez", (float)4000),
                new EmployeeDto(3, "IT", "Michael", "Cooper", (float)5000)
        );
        final List<EmployeeDto> ret = empService.getAllEmployees();
        assertThat(ret).isNotNull();
        assertThat(ret).hasSameSizeAs(lst);
        assertThat(ret).hasSameElementsAs(lst);

        ret.forEach(System.out::println);
    }

    @Test
    public void canViewDepartment() {
        final EmployeeDto ret = empService.get(1L);
        assertThat(ret).isNotNull();
        assertThat(ret).isEqualTo(new EmployeeDto(1, "MU", "Dave", "Johnson", (float)3400));

        System.out.println(ret);
    }
}
