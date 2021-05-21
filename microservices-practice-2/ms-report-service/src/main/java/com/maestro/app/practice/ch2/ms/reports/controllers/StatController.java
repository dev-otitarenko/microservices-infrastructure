package com.maestro.app.practice.ch2.ms.reports.controllers;

import com.maestro.app.practice.ch2.ms.reports.clients.DepartmentService;
import com.maestro.app.practice.ch2.ms.reports.clients.EmployeeService;
import com.maestro.app.practice.ch2.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch2.ms.reports.domain.EmployeeDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.maestro.app.practice.ch2.ms.reports.controllers.Resilience4jConstants.STAT_CLIENT;

@Slf4j
@RestController
@AllArgsConstructor
@RateLimiter(name = STAT_CLIENT)
@Retry(name = STAT_CLIENT)
@Bulkhead(name = STAT_CLIENT)
public class StatController {
    private final DepartmentService deptService;
    private final EmployeeService empService;

    @GetMapping(value = "/summary")
    @CircuitBreaker(name = STAT_CLIENT, fallbackMethod = "defaultSummary")
    public Map<String, Object> getSummary() {
        Map<String, Object> r = new LinkedHashMap<>();

        List<DepartmentDto> depts = deptService.getAllDepartments();
        List<EmployeeDto> emps = empService.getAllEmployees();

        r.put("countDepts", depts.size());

        long cntEmployees = emps.size();
        double summSalary = emps.stream()
                .mapToDouble(EmployeeDto::getSalary)
                .sum();
        r.put("countEmps", cntEmployees);
        r.put("salary", summSalary);

        return r;
    }

    @GetMapping(value = "/summary/dept")
    @CircuitBreaker(name = STAT_CLIENT, fallbackMethod = "defaultStat")
    public List<Map<String, Object>> getDeptsSummary() {
        List<Map<String, Object>> ret = new ArrayList<>();

        List<DepartmentDto> depts = deptService.getAllDepartments();
        List<EmployeeDto> emps = empService.getAllEmployees();

        depts.forEach(d -> {
            Map<String, Object> r = new LinkedHashMap<>();
            r.put("deptNum", d.getDeptNum());
            r.put("name", d.getName());

            long cnt = emps.stream()
                            .filter(e -> e.getDeptId().equals(d.getDeptNum()))
                            .count();
            double summ = emps.stream()
                            .filter(e -> e.getDeptId().equals(d.getDeptNum()))
                            .mapToDouble(EmployeeDto::getSalary)
                            .sum();

            r.put("countEmps", cnt);
            r.put("salary", summ);

            ret.add(r);
        });

        return ret;
    }

    private Map<String, Object> defaultSummary(Throwable ex) {
        log.warn("Fallback while getting the list of departments/employees:  {}.", ex.getMessage());
        return new HashMap<>();
    }

    private  List<Map<String, Object>> defaultStat(Throwable ex) {
        log.warn("Fallback while getting the list of departments/employees:  {}.", ex.getMessage());
        return new ArrayList<>();
    }
}
