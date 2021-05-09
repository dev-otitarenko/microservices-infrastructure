package com.maestro.app.ms.practice.employees.controllers;

import com.maestro.app.ms.practice.employees.entities.Employee;
import com.maestro.app.ms.practice.employees.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService empService;

    @GetMapping("/emp")
    public List<Employee> getList() {
        return empService.getList();
    }

    @GetMapping("/emp/{id}")
    public Employee get(@PathVariable Long id) {
        return empService.get(id);
    }

    @DeleteMapping("/emp/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Employee dept = empService.get(id);
        if (dept != null)
            empService.delete(dept);
    }

    @PutMapping("/emp")
    @Transactional
    public void create(@RequestBody @Valid Employee dept) {
        empService.save(dept);
    }

    @PostMapping("/emp/{id}")
    @Transactional
    public void update(@PathVariable Long id, @RequestBody @Valid Employee dept) {
        dept.setId(id);
        empService.save(dept);
    }
}
