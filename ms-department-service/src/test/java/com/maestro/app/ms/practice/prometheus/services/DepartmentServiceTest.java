package com.maestro.app.ms.practice.prometheus.services;

import com.maestro.app.ms.practice.prometheus.entities.Department;
import com.maestro.app.ms.practice.prometheus.repositories.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class DepartmentServiceTest {
    @Autowired
    public DepartmentRepository deptRepository;

    @Test
    public void checkRepository() {
        Department dept = new Department();
        dept.setDeptNumber("IT");
        dept.setName("IT Unit");

        deptRepository.save(dept);
    }
}