package com.wserp.orgmembersservice.controller;

import com.wserp.orgmembersservice.service.OrgMembersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org-members")
@RequiredArgsConstructor
public class OrgMembersController {

    private final ModelMapper modelMapper;
    private final OrgMembersService orgMembersService;
    @Value("${internal.token}")
    private String internalToken;


}
