package com.wserp.orgmembersservice.controller;

import com.wserp.common.dto.OrgMemberDto;
import com.wserp.orgmembersservice.service.OrgMembersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org-members")
@RequiredArgsConstructor
public class OrgMembersController {

    private final ModelMapper modelMapper;
    private final OrgMembersService orgMembersService;

    @GetMapping("/orgId/{orgId}")
    public ResponseEntity<List<OrgMemberDto>> getAllOrgMembers(@PathVariable String orgId) {
        return ResponseEntity.ok(orgMembersService.getAllOrgMembers(orgId).stream().map(org -> modelMapper.map(org, OrgMemberDto.class)).toList());
    }

    @DeleteMapping("/orgId/{orgId}/userId/{userId}")
    public ResponseEntity<String> deleteOrgMember(@PathVariable String orgId, @PathVariable String userId) {
        orgMembersService.deleteOrgMember(orgId, userId);
        return ResponseEntity.ok("Org member deleted successfully");
    }


}
