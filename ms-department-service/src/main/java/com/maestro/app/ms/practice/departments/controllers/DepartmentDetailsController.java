package com.maestro.app.ms.practice.departments.controllers;

import com.maestro.app.ms.practice.departments.entities.Department;
import com.maestro.app.ms.practice.departments.entities.Employee;
import com.maestro.app.ms.practice.departments.services.DepartmentService;
import com.maestro.app.ms.practice.departments.services.EmployeeService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maestro.app.ms.practice.departments.controllers.Resilience4jConstants.*;

@Slf4j
@RestController
@AllArgsConstructor
@RateLimiter(name = PERSON_CLIENT)
@Retry(name = PERSON_CLIENT)
@Bulkhead(name = PERSON_CLIENT)
public class DepartmentDetailsController {
    private final DepartmentService deptService;
    private final EmployeeService empService;

    @GetMapping("/dept/{id}/details")
    @CircuitBreaker(name = PERSON_CLIENT, fallbackMethod = "defaultDetail")
    public Map<String, Object> getDetails(@PathVariable Long id) {
        log.info("Get the list of employees in the specific department \"{}\".", id);

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

    private Map<String, Object> defaultDetail(Long id, Throwable ex) {
        log.warn("Fallback while getting the list of employees in the specific department \"{}\":  {}.", id, ex.getMessage());
        return new HashMap<>();
    }
}
