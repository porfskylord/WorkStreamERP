package com.wserp.organizationservice.service;

import com.wserp.common.dto.OrgUpdateRequest;
import com.wserp.common.exception.NotFoundException;
import com.wserp.organizationservice.entity.Organization;
import com.wserp.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Organization getOrganizationById(String id) {
        return organizationRepository.findById(id).orElseThrow(() -> new NotFoundException("Organization not found"));
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization updateMyOrganization(String currentUserId, OrgUpdateRequest orgUpdateRequest) {
        Organization organization = organizationRepository.findById(currentUserId).orElseThrow(() -> new NotFoundException("Organization not found"));
        organization.setName(orgUpdateRequest.getName());
        organization.setUpdatedBy(currentUserId);
        organization.setUpdatedBy(currentUserId);

        return organizationRepository.save(organization);

    }

    public void deleteMyOrganization(String currentUserId) {
        Organization organization = organizationRepository.findById(currentUserId).orElseThrow(() -> new NotFoundException("Organization not found"));
        organization.setDeleted(true);
        organization.setDeletedBy(currentUserId);
    }
}
