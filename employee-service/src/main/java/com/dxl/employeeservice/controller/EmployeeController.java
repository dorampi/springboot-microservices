package com.dxl.employeeservice.controller;

import com.dxl.employeeservice.dto.APIResponseDto;
import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    // Build Save Employee REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Build Get Employee REST API
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> createEmployee(@PathVariable("id") Long employeeId) {
        APIResponseDto foundEmployee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(foundEmployee, HttpStatus.CREATED);
    }
}
