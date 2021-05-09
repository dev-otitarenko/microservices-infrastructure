package com.maestro.app.ms.practice.employees.controllers;

import com.maestro.app.ms.practice.employees.entities.Employee;
import com.maestro.app.ms.practice.employees.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class DepartmentController {
    private final EmployeeService empService;

    @GetMapping("/dept/{deptId}")
    public List<Employee> getListEmployees(@PathVariable String deptId) {
        return empService.getDeptEmployees(deptId);
    }
}
