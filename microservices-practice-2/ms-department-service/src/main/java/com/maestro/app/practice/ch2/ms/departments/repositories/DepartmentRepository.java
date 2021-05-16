package com.maestro.app.practice.ch2.ms.departments.repositories;

import com.maestro.app.practice.ch2.ms.departments.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
