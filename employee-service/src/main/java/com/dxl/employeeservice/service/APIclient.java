package com.dxl.employeeservice.service;

import com.dxl.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE")
public interface APIclient {

    // Build get department REST API
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartmentByDepartmentCode(@PathVariable("department-code") String departmentCode);
}
