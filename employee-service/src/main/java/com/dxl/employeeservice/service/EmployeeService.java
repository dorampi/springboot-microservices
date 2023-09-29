package com.dxl.employeeservice.service;

import com.dxl.employeeservice.dto.APIResponseDto;
import com.dxl.employeeservice.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
