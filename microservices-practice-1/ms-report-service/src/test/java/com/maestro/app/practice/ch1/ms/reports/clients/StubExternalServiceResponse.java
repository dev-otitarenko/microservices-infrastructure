package com.maestro.app.practice.ch1.ms.reports.clients;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.maestro.app.practice.ch1.ms.reports.domain.DepartmentDto;
import com.maestro.app.practice.ch1.ms.reports.domain.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StubExternalServiceResponse extends ResponseDefinitionTransformer {
    private static final String TRANSFORMER_NAME = "body-transformer";
    private static final boolean APPLY_GLOBALLY = true;

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static List<DepartmentDto> lstDepts = Arrays.asList(
            new DepartmentDto(1, "MU", "Management Unit"),
            new DepartmentDto(2, "HR", "Human Resources Unit"),
            new DepartmentDto(1, "IT", "IT Unit")
    );
    private static List<EmployeeDto> lstEmps = Arrays.asList(
            new EmployeeDto(1, "MU", "Dave", "Johnson", (float)3400),
            new EmployeeDto(2, "HR", "Tom", "Fernandez", (float)4000),
            new EmployeeDto(3, "IT", "Michael", "Cooper", (float)5000)
    );

    @Override
    public ResponseDefinition transform(Request request,
                                        ResponseDefinition responseDefinition,
                                        FileSource fileSource,
                                        Parameters parameters) {
        final String path = request.getUrl();
        System.out.println("StubResponse: " + path);
        String transformedJson = "";
        if (path.startsWith("/dept")) {
            if (path.equals("/dept") || path.equals("/dept/")) {
                transformedJson = conv2json(lstDepts);
            } else {
                final String[] pairs = path.split("/");
                final long deptId = Long.parseLong(pairs[2]);
                transformedJson = conv2json(lstDepts.stream().filter(d -> d.getId() == deptId).findFirst().orElse(null));
            }
        } else if (path.startsWith("/emp")) {
            if (path.equals("/emp") || path.equals("/emp/")) {
                transformedJson = conv2json(lstEmps);
            } else {
                final String[] pairs = path.split("/");
                final long empId = Long.parseLong(pairs[2]);
                transformedJson = conv2json(lstEmps.stream().filter(d -> d.getId() == empId).findFirst().orElse(null));
            }
        } else {
            return new ResponseDefinitionBuilder()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())
                       // .withBody(null)
                        .build();
        }
        return new ResponseDefinitionBuilder()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(HttpStatus.OK.value())
                .withBody(transformedJson)
                .build();
    }

    private String conv2json(Object obj) {
        try {
            return jsonMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getName() {
        return TRANSFORMER_NAME;
    }

    @Override
    public boolean applyGlobally() {
        return APPLY_GLOBALLY;
    }
}
