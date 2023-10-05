package com.dxl.employeeservice.service.impl;

import com.dxl.employeeservice.dto.APIResponseDto;
import com.dxl.employeeservice.dto.DepartmentDto;
import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.entity.Employee;
import com.dxl.employeeservice.exception.ResourceNotFoundException;
import com.dxl.employeeservice.repository.EmployeeRepository;
import com.dxl.employeeservice.service.APIClient;
import com.dxl.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.dxl.employeeservice.mapper.AutoEmployeeMapper.AUTO_EMPLOYEE_MAPPER;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

//    private RestTemplate restTemplate;

    private WebClient webClient;

//    private APIClient apIclient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = AUTO_EMPLOYEE_MAPPER.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(savedEmployee);
    }

//    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById() method");
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

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

//        DepartmentDto departmentDto = apIclient.getDepartmentByDepartmentCode(employee.getDepartmentCode());

        EmployeeDto employeeDto = AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(employee);


        return new APIResponseDto(employeeDto, departmentDto);
    }


    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId));

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("DxL Department");
        departmentDto.setDepartmentCode("DXL000");
        departmentDto.setDepartmentDescription("DxL Default Department");

        EmployeeDto employeeDto = AUTO_EMPLOYEE_MAPPER.mapToEmployeeDto(employee);

        return new APIResponseDto(employeeDto, departmentDto);
    }
}
