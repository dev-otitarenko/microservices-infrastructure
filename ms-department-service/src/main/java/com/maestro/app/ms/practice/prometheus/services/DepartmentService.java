package com.maestro.app.ms.practice.prometheus.services;

import com.maestro.app.ms.practice.prometheus.entities.Department;
import com.maestro.app.ms.practice.prometheus.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository deptRepository;

    public List<Department> getList() {
        return deptRepository.findAll();
    }

    public Department get(Long id) {
        Optional<Department>  rec = deptRepository.findById(id);
        return rec.orElse(null);
    }

    public void save(Department dept) {
        deptRepository.save(dept);
    }

    public void delete(Department dept) {
        deptRepository.delete(dept);
    }
}
