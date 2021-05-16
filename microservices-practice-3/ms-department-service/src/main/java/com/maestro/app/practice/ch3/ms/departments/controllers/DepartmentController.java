package com.maestro.app.practice.ch3.ms.departments.controllers;

import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import com.maestro.app.practice.ch3.ms.departments.services.DepartmentService;
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
public class DepartmentController {
    private final DepartmentService deptService;

    @GetMapping("/dept")
    public List<Department> getList() {
        log.info("Get the list of departments.");
        return deptService.getList();
    }

    @GetMapping("/dept/{id}")
    public Department get(@PathVariable Long id) {
        log.info("Get the department record \"{}\".", id);
        return deptService.get(id);
    }

    @DeleteMapping("/dept/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        log.info("Removing the specific department \"{}\".", id);
        Department dept = deptService.get(id);
        if (dept != null) {
            deptService.delete(dept);
            log.info("Removing completed");
        }
    }

    @PutMapping("/dept")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void create(@RequestBody @Valid Department dept) {
        log.info("Creating the department.");
        deptService.save(dept);
        log.info("Creating completed");
    }

    @PostMapping("/dept/{id}")
    @Transactional
    public void update(@PathVariable Long id, @RequestBody @Valid Department dept) {
        log.info("Updating the specific department \"{}\".", id);
        dept.setId(id);
        deptService.save(dept);
        log.info("Updating completed");
    }
}
