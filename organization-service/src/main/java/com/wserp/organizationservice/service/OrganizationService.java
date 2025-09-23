package com.wserp.organizationservice.service;

import com.wserp.common.dto.OrganizationRequest;
import com.wserp.common.exception.ConfilictException;
import com.wserp.organizationservice.entity.Organization;
import com.wserp.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    @Value("${app.system-user-id}")
    private String systemUserId;


    public Organization saveOrganization(OrganizationRequest request) {
        if (organizationRepository.findByName(request.getName()).isPresent()) {
            throw new ConfilictException("Organization already exists");
        }

        Organization organization = Organization.builder()
                .name(request.getName())
                .ownerId(request.getOwnerId())
                .build();
        organization.setCreatedBy(systemUserId);
        organization.setUpdatedBy(systemUserId);

        return organizationRepository.save(organization);
    }
}
