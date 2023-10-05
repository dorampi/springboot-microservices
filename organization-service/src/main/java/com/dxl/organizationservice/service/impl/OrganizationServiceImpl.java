package com.dxl.organizationservice.service.impl;

import com.dxl.organizationservice.dto.OrganizationDto;
import com.dxl.organizationservice.entity.Organization;
import com.dxl.organizationservice.exception.ResourceNotFoundException;
import com.dxl.organizationservice.repository.OrganizationRepository;
import com.dxl.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dxl.organizationservice.mapper.AutoOrganizationMapper.AUTO_ORGANIZATION_MAPPER;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Organization organization = AUTO_ORGANIZATION_MAPPER.mapToOrganization(organizationDto);

        Organization savedOrganization = organizationRepository.save(organization);

        return AUTO_ORGANIZATION_MAPPER.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {

        Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "OrganizationCode", organizationCode));

        return AUTO_ORGANIZATION_MAPPER.mapToOrganizationDto(organization);
    }
}
