package com.maestro.app.practice.ch2.ms.employees.controllers;

import com.maestro.app.practice.ch2.ms.employees.entities.Employee;
import com.maestro.app.practice.ch2.ms.employees.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        log.info("Get the list of employees");
        return empService.getList();
    }

    @GetMapping("/emp/{id}")
    public Employee get(@PathVariable Long id) {
        log.info("Get the employee record \"{}\"", id);
        return empService.get(id);
    }

    @DeleteMapping("/emp/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        log.info("Delete the employee record \"{}\"", id);
        Employee dept = empService.get(id);
        if (dept != null)
            empService.delete(dept);
    }

    @PutMapping("/emp")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@RequestBody @Valid Employee dept) {
        log.info("Create the employee record");
        empService.save(dept);
    }

    @PostMapping("/emp/{id}")
    @Transactional
    public void update(@PathVariable Long id, @RequestBody @Valid Employee dept) {
        log.info("Update the employee record \"{}\"", id);
        dept.setId(id);
        empService.save(dept);
    }
}
