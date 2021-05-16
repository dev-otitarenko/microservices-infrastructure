package com.maestro.app.practice.ch3.ms.departments.repositories;

import com.maestro.app.practice.ch3.ms.departments.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
