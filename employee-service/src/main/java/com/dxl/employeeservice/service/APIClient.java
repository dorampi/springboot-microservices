package com.dxl.employeeservice.service;

import com.dxl.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClient {

    // Build get department REST API
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartmentByDepartmentCode(@PathVariable("department-code") String departmentCode);
}
