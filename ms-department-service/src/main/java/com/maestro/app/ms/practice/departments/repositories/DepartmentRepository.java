package com.maestro.app.ms.practice.departments.repositories;

import com.maestro.app.ms.practice.departments.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
