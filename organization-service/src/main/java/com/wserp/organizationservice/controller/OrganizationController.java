package com.wserp.organizationservice.controller;


import com.wserp.common.dto.OrganizationDto;
import com.wserp.common.dto.OrganizationRequest;
import com.wserp.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orgs")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final ModelMapper modelMapper;
    @Value("${internal.token}")
    private String internalToken;

    @PostMapping("/save")
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationRequest organization, @RequestHeader(value = "X-INTERNAL-TOKEN", required = false) String token) {
        if (!internalToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return ResponseEntity.ok(modelMapper.map(organizationService.saveOrganization(organization), OrganizationDto.class));
    }


}
