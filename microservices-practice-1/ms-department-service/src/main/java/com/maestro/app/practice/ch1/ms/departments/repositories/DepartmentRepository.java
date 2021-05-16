package com.maestro.app.practice.ch1.ms.departments.repositories;

import com.maestro.app.practice.ch1.ms.departments.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
