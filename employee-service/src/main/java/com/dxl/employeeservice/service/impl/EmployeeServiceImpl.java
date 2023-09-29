package com.dxl.employeeservice.service.impl;

import com.dxl.employeeservice.dto.APIResponseDto;
import com.dxl.employeeservice.dto.DepartmentDto;
import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.entity.Employee;
import com.dxl.employeeservice.exception.ResourceNotFoundException;
import com.dxl.employeeservice.repository.EmployeeRepository;
import com.dxl.employeeservice.service.APIclient;
import com.dxl.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.dxl.employeeservice.mapper.AutoEmployeeMapper.AUTO_EMPLOYEE_MAPPER;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

//    private RestTemplate restTemplate;

//    private WebClient webClient;

    private APIclient apIclient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = AUTO_EMPLOYEE_MAPPER.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId));

        // instead of fetching DepartmentDTO we could fetch only department code
        // reasoning is , employee service shall not know about department service too much,
        // if needed we could fetch required data from department service
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(
//                "http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
//                DepartmentDto.class
//        );
//        DepartmentDto departmentDto = responseEntity.getBody();

//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        DepartmentDto departmentDto = apIclient.getDepartmentByDepartmentCode(employee.getDepartmentCode());

        EmployeeDto employeeDto = AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(employee);


        return new APIResponseDto(employeeDto, departmentDto);
    }
}
