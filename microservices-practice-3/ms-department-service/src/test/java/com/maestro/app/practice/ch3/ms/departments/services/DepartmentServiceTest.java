package com.maestro.app.practice.ch3.ms.departments.services;

import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import com.maestro.app.practice.ch3.ms.departments.repositories.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
class DepartmentServiceTest {
    @Autowired
    public DepartmentRepository deptRepository;

    @Test
    public void checkRepository() {
//        Department dept = new Department();
//        dept.setDeptNum("IT");
//        dept.setName("IT Unit");
//
//        dept = deptRepository.save(dept);
    }
}