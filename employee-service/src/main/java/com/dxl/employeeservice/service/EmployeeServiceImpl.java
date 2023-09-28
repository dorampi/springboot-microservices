package com.dxl.employeeservice.service;

import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.entity.Employee;
import com.dxl.employeeservice.exception.ResourceNotFoundException;
import com.dxl.employeeservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dxl.employeeservice.mapper.AutoEmployeeMapper.AUTO_EMPLOYEE_MAPPER;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = AUTO_EMPLOYEE_MAPPER.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId));

        return AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(employee);
    }
}
