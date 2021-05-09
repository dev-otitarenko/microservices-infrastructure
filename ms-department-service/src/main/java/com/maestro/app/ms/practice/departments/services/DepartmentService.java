package com.maestro.app.ms.practice.departments.services;

import com.maestro.app.ms.practice.departments.entities.Department;
import com.maestro.app.ms.practice.departments.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    public Department save(Department dept) {
        return deptRepository.save(dept);
    }

    public void delete(Department dept) {
        deptRepository.delete(dept);
    }

    public void  saveFile(final InputStream inputStream) throws IOException {
        final long start = System.currentTimeMillis();

        List<Department> depts = parseCSVFile(inputStream);

        log.info("\tSaving a list of {} records", depts.size());

        deptRepository.saveAll(depts);

        log.info("\tElapsed time: {}", (System.currentTimeMillis() - start));
    }

    private List<Department> parseCSVFile(final InputStream inputStream) throws IOException {
        final List<Department> res = new ArrayList<>();

        log.info("\tParsing CSV file");

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] data = line.split(";");
                final Department dpt = new Department();
                dpt.setDeptNumber(data[0]);
                dpt.setName(data[1]);
                res.add(dpt);
            }
        }

        log.info("\tHaving {} records", res.size());

        return res;
    }
}
