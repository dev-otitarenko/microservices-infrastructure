package com.maestro.app.practice.ch3.ms.departments.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable=false)
    private String deptNum;

    @NotNull
    @Column(nullable=false)
    private String name;

    @Override
    public String toString() {
        return String.format("{ id: %d, deptNum: '%s', name: '%s' }", id, deptNum, name);
    }
}
