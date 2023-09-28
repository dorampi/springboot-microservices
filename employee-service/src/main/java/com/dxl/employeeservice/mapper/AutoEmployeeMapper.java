package com.dxl.employeeservice.mapper;

import com.dxl.employeeservice.dto.EmployeeDto;
import com.dxl.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoEmployeeMapper {

    AutoEmployeeMapper AUTO_EMPLOYEE_MAPPER = Mappers.getMapper(AutoEmployeeMapper.class);

    EmployeeDto mapToEmployeeDto(Employee employee);

    Employee mapToEmployee(EmployeeDto employeeDto);
}
