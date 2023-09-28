package com.dxl.departmentservice.service.impl;

import com.dxl.departmentservice.dto.DepartmentDto;
import com.dxl.departmentservice.entity.Department;
import com.dxl.departmentservice.exception.ResourceNotFoundException;
import com.dxl.departmentservice.repository.DepartmentRepository;
import com.dxl.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dxl.departmentservice.mapper.AutoDepartmentMapper.AUTO_DEPARTMENT_MAPPER;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = AUTO_DEPARTMENT_MAPPER.mapToDepartment(departmentDto);

        Department savedDepartment = departmentRepository.save(department);

        return AUTO_DEPARTMENT_MAPPER.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "Code", departmentCode));

        return AUTO_DEPARTMENT_MAPPER.mapToDepartmentDto(department);
    }
}
