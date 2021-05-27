package com.maestro.app.practice.ch2.ms.reports.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
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
public class DepartmentServiceIT {
    @Autowired
    private WireMockServer mockService;

    @Autowired
    private DepartmentService deptService;

    @Test
    public void canViewAllDepartments() {
        final List<DepartmentDto> lst = Arrays.asList(
                new DepartmentDto(1, "MU", "Management Unit"),
                new DepartmentDto(2, "HR", "Human Resources Unit"),
                new DepartmentDto(1, "IT", "IT Unit")
        );
        final List<DepartmentDto> ret = deptService.getAllDepartments();
        assertThat(ret).isNotNull();
        assertThat(ret).hasSameSizeAs(lst);
        assertThat(ret).hasSameElementsAs(lst);

        ret.forEach(System.out::println);
    }

    @Test
    public void canViewDepartment() {
        final DepartmentDto ret = deptService.get(1L);
        assertThat(ret).isNotNull();
        assertThat(ret).isEqualTo(new DepartmentDto(1, "MU", "Management Unit"));

        System.out.println(ret);
    }
}
