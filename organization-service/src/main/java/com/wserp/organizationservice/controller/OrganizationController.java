package com.wserp.organizationservice.controller;


import com.wserp.common.dto.OrgUpdateRequest;
import com.wserp.common.dto.OrganizationDto;
import com.wserp.common.jwt.CurrentUserData;
import com.wserp.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgs")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final ModelMapper modelMapper;
    private final CurrentUserData currentUserData;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(organizationService.getOrganizationById(id), OrganizationDto.class));
    }

    @GetMapping("/my")
    public ResponseEntity<OrganizationDto> getMyOrganization() {
        return ResponseEntity.ok(modelMapper.map(organizationService.getOrganizationById(currentUserData.getCurrentUserId()), OrganizationDto.class));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations().stream().map(org -> modelMapper.map(org, OrganizationDto.class)).toList());
    }

    @PutMapping("/my")
    public ResponseEntity<OrganizationDto> updateMyOrganization(@RequestBody OrgUpdateRequest orgUpdateRequest) {
        return ResponseEntity.ok(modelMapper.map(organizationService.updateMyOrganization(currentUserData.getCurrentUserId(), orgUpdateRequest), OrganizationDto.class));
    }

    @DeleteMapping("/my")
    public ResponseEntity<String> deleteMyOrganization() {
        organizationService.deleteMyOrganization(currentUserData.getCurrentUserId());
        return ResponseEntity.ok("Organization deleted successfully");
    }

}
