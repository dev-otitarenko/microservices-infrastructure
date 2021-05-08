package com.maestro.app.ms.practice.prometheus.controllers;

import com.maestro.app.ms.practice.prometheus.entities.Department;
import com.maestro.app.ms.practice.prometheus.services.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService deptService;

    @GetMapping("/")
    public String welcome() {
        log.info("Welcome API call");
        return "Hello from test service!!";
    }

    @GetMapping("/dept")
    public List<Department> getList() {
        return deptService.getList();
    }

    @GetMapping("/dept/{id}")
    public Department get(@PathVariable Long id) {
        return deptService.get(id);
    }

    @DeleteMapping("/dept/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Department dept = deptService.get(id);
        if (dept != null)
            deptService.delete(dept);
    }

    @PutMapping("/dept")
    @Transactional
    public void create(@RequestBody @Valid Department dept) {
        deptService.save(dept);
    }

    @PostMapping("/dept/{id}")
    @Transactional
    public void update(@PathVariable Long id, @RequestBody @Valid Department dept) {
        dept.setId(id);
        deptService.save(dept);
    }
}
