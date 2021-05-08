package com.maestro.app.ms.practice.prometheus.repositories;

import com.maestro.app.ms.practice.prometheus.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
