package com.maestro.app.ms.practice.employees.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable=false)
    private String deptNumber;

    @NotNull
    @Column(nullable=false)
    private String firstName;

    @NotNull
    @Column(nullable=false)
    private String familyName;

    @NotNull
    @Column(nullable=false)
    private Float salary;
}
