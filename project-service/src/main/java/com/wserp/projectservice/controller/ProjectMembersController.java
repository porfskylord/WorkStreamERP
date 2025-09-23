package com.wserp.projectservice.controller;

import com.wserp.projectservice.dto.ProjectMembersDto;
import com.wserp.projectservice.dto.request.AddProjectMembers;
import com.wserp.projectservice.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project Members Controller", description = "Project Members Controller")
public class ProjectMembersController {
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @PostMapping("/{projectId}/members")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProjectMembersDto> addProjectMembers(@PathVariable String projectId, @RequestBody AddProjectMembers addProjectMembers) {
        return ResponseEntity.ok(modelMapper.map(projectService.addProjectMembers(projectId, addProjectMembers), ProjectMembersDto.class));
    }

    @GetMapping("/{projectId}/members")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ProjectMembersDto>> getProjectMembers(@PathVariable String projectId) {
        return ResponseEntity.ok(projectService.getProjectMembers(projectId).stream().map(projectMembers -> modelMapper.map(projectMembers, ProjectMembersDto.class)).toList());
    }

    @DeleteMapping("/{projectId}/members/{memberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> deleteProjectMember(@PathVariable String projectId, @PathVariable String memberId) {
        projectService.deleteProjectMember(projectId, memberId);
        return ResponseEntity.ok().build();
    }
}
