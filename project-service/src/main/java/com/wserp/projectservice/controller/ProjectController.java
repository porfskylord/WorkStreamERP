package com.wserp.projectservice.controller;

import com.wserp.projectservice.dto.ProjectDto;
import com.wserp.projectservice.dto.request.ProjectRequest;
import com.wserp.projectservice.dto.request.UpdateProjectRequest;
import com.wserp.projectservice.dto.request.UpdateStatus;
import com.wserp.projectservice.filter.CurrentUserData;
import com.wserp.projectservice.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "Project Controller")
public class ProjectController {
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final CurrentUserData currentUserData;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProjectDto> createProject(@RequestBody @Valid ProjectRequest projectRequest) {
        return ResponseEntity.ok(modelMapper.map(projectService.createProject(projectRequest), ProjectDto.class));
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(projectService.getProjectById(id), ProjectDto.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String id, @RequestBody UpdateProjectRequest updateProjectRequest) {
        return ResponseEntity.ok(modelMapper.map(projectService.updateProject(id, updateProjectRequest), ProjectDto.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProjectDto> updateProjectStatus(@PathVariable String id, @RequestBody UpdateStatus updateStatus) {
        return ResponseEntity.ok(modelMapper.map(projectService.updateProjectStatus(id, updateStatus, currentUserData.getCurrentUserId()), ProjectDto.class));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProjectDto>> getAllProjects(@RequestParam(required = false) String clientId, @RequestParam(required = false) String status) {
        return ResponseEntity.ok(projectService.getAllProjects(clientId, status).stream()
                .map(project -> modelMapper.map(project, ProjectDto.class)).toList());
    }

    @GetMapping("/my-projects")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ProjectDto>> getMyProjects() {
        return ResponseEntity.ok(projectService.getProjectsByClientId(currentUserData.getCurrentUserId()).stream()
                .map(project -> modelMapper.map(project, ProjectDto.class)).toList());
    }
}
