package com.maestro.app.practice.ch2.ms.reports.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private long id;
    private String deptId;
    private String firstName;
    private String familyName;
    private Float salary;
}
