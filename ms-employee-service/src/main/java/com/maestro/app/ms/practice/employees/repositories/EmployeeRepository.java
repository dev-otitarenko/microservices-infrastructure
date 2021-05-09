package com.maestro.app.ms.practice.employees.repositories;

import com.maestro.app.ms.practice.employees.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
