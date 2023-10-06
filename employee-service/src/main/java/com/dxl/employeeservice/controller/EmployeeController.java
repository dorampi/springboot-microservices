package com.dxl.employeeservice.controller;

import com.dxl.employeeservice.dto.APIResponseDto;
import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Employee Service - Employee Controller",
        description = "Employee Controller Exposes REST APIs for Employee-Service"
)
@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @Operation(
            summary = "Save Employee REST API",
            description = "Save Employee REST API is used to save employee object in the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // Build Save Employee REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Employee REST API",
            description = "Get Employee REST API is used to get an employee object from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    // Build Get Employee REST API
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getEmployee(@PathVariable("id") Long employeeId) {
        APIResponseDto foundEmployee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(foundEmployee, HttpStatus.CREATED);
    }
}
