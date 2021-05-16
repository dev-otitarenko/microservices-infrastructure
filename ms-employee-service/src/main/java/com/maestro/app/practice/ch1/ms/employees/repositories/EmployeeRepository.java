package com.maestro.app.practice.ch1.ms.employees.repositories;

import com.maestro.app.ms.practice.employees.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT v FROM Employee v WHERE v.deptId = :deptId")
    List<Employee> getDeptEmployees(@Param("deptId") String deptId);
}
