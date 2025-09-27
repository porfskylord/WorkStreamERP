package com.wserp.inviteservice.controller;

import com.wserp.common.dto.AcceptInviteRequest;
import com.wserp.common.dto.InviteDto;
import com.wserp.inviteservice.dto.request.InviteRequest;
import com.wserp.inviteservice.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<String> createInvite(@RequestBody InviteRequest inviteRequest) {
        return ResponseEntity.ok(inviteService.createInvite(inviteRequest));
    }

    @PostMapping("/token/{token}/accept")
    public ResponseEntity<?> acceptInvite(@PathVariable String token, @RequestBody AcceptInviteRequest acceptInviteRequest) {
        return ResponseEntity.ok(inviteService.acceptInvite(token, acceptInviteRequest));
    }

    @GetMapping("/all-pending")
    public ResponseEntity<List<InviteDto>> getAllPendingInvites() {
        return ResponseEntity.ok(inviteService.getAllPendingInvites().stream().map(invite -> modelMapper.map(invite, InviteDto.class)).toList());
    }


}
