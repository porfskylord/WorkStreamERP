package com.wserp.projectservice.service;

import com.wserp.projectservice.dto.request.AddProjectMembers;
import com.wserp.projectservice.dto.request.ProjectRequest;
import com.wserp.projectservice.dto.request.UpdateProjectRequest;
import com.wserp.projectservice.dto.request.UpdateStatus;
import com.wserp.projectservice.entity.Project;
import com.wserp.projectservice.entity.ProjectMembers;
import com.wserp.projectservice.entity.enums.ProjectStatus;
import com.wserp.projectservice.exeptions.ConfilictException;
import com.wserp.projectservice.exeptions.NotFoundException;
import com.wserp.projectservice.filter.CustomPrincipal;
import com.wserp.projectservice.repository.ProjectMembersRepository;
import com.wserp.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMembersRepository projectMembersRepository;

    //-------------------------------------------------------------Project Service Methods---------------------------------------------------------------------

    public Project createProject(ProjectRequest request) {
        CustomPrincipal customPrincipal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(projectRepository.findProjectByName(request.getName()).isPresent()) {
            throw new ConfilictException("Project already exists");
        }

        Project project = new Project();
        project.setId(UUID.randomUUID().toString());
        project.setName(request.getName());
        project.setClientId(customPrincipal.getUserId());
        project.setClientName(customPrincipal.getUsername());
        project.setDescription(request.getDescription());
        project.setStatus(ProjectStatus.NEW);


        return projectRepository.save(project);
    }


    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }


    public Project updateProject(String id, UpdateProjectRequest request) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));

        if(request.getName() != null) {
            project.setName(request.getName());
        }
        if(request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if(request.getStatus() != null) {
            project.setStatus(request.getStatus());
        }
        if(request.getStartDate() != null) {
            project.setStartDate(request.getStartDate());
        }
        if(request.getEndDate() != null) {
            project.setEndDate(request.getEndDate());
        }

        return projectRepository.save(project);
    }

    public void deleteProject(String id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        project.setStatus(ProjectStatus.CANCELLED);
        projectRepository.save(project);
    }

    public Project updateProjectStatus(String id, UpdateStatus updateStatus) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));

        if(updateStatus.getStatus() == ProjectStatus.IN_PROGRESS){
            project.setStatus(ProjectStatus.IN_PROGRESS);
            project.setStartDate(LocalDate.now());
        } else if(updateStatus.getStatus() == ProjectStatus.COMPLETED){
            project.setStatus(ProjectStatus.COMPLETED);
            project.setEndDate(LocalDate.now());
        } else {
            project.setStatus(updateStatus.getStatus());
        }

        return projectRepository.save(project);

    }

    public List<Project> getAllProjects(String clientId, String status) {
        if(clientId != null && status != null) {
            return projectRepository.findAllByClientIdAndStatus(clientId, ProjectStatus.valueOf(status));
        } else if(clientId != null) {
            return projectRepository.findAllByClientId(clientId);
        } else if(status != null) {
            return projectRepository.findAllByStatus(ProjectStatus.valueOf(status));
        } else {
            return projectRepository.findAll();
        }
    }

    public List<Project> getProjectsByClientId(String clientId) {
        return projectRepository.findAllByClientId(clientId);
    }

    //-----------------------------------------------------------Project Members Service Methods----------------------------------------------------------------

    public ProjectMembers addProjectMembers(String projectId, AddProjectMembers addProjectMembers) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));

        ProjectMembers projectMembers = new ProjectMembers();
        projectMembers.setId(UUID.randomUUID().toString());
        projectMembers.setProject(project);
        projectMembers.setUserId(addProjectMembers.getUserId());
        projectMembers.setRole(addProjectMembers.getRole());

        return projectMembersRepository.save(projectMembers);


    }

    public List<ProjectMembers> getProjectMembers(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        return projectMembersRepository.findAllByProjectId(project.getId());
    }

    public void deleteProjectMember(String projectId, String memberId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        ProjectMembers projectMembers = projectMembersRepository.findByProjectIdAndUserId(project.getId(), memberId).orElseThrow(() -> new NotFoundException("Project member not found"));
        projectMembersRepository.delete(projectMembers);
    }
}
