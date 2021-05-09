package com.maestro.app.ms.practice.departments.controllers;

import com.maestro.app.ms.practice.departments.entities.Department;
import com.maestro.app.ms.practice.departments.entities.Employee;
import com.maestro.app.ms.practice.departments.services.DepartmentService;
import com.maestro.app.ms.practice.departments.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentService deptService;
    private final EmployeeService empService;

    @GetMapping("/dept")
    public List<Department> getList() {
        return deptService.getList();
    }

    @GetMapping("/dept/{id}")
    public Department get(@PathVariable Long id) {
        return deptService.get(id);
    }

    @GetMapping("/dept/{id}/details")
    public Map<String, Object> getDetails(@PathVariable Long id) {
        Map<String, Object> ret = new HashMap<>();

        Department dept = deptService.get(id);
        if (dept != null) {
            ret.put("id", dept.getId());
            ret.put("deptNum", dept.getDeptNum());
            ret.put("name", dept.getName());

            List<Map<String, Object>> emps = new ArrayList<>();
            List<Employee> lstEmps = empService.getDeptEmployees(dept.getDeptNum());
            lstEmps.forEach(emp -> {
                Map<String, Object> empRow = new HashMap<>();
                empRow.put("firstName", emp.getFirstName());
                empRow.put("familyName", emp.getFamilyName());
                empRow.put("salary", emp.getSalary());

                emps.add(empRow);
            });
            ret.put("employees", emps);
        }

        return ret;
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
