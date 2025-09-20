package com.wserp.projectservice.service;

import com.wserp.commondto.ProjectMemberEvent;
import com.wserp.enums.ErrorMessage;
import com.wserp.projectservice.client.UserServiceClient;
import com.wserp.projectservice.dto.MemberDto;
import com.wserp.projectservice.dto.request.AddProjectMembers;
import com.wserp.projectservice.dto.request.ProjectRequest;
import com.wserp.projectservice.dto.request.UpdateProjectRequest;
import com.wserp.projectservice.dto.request.UpdateStatus;
import com.wserp.projectservice.entity.Project;
import com.wserp.projectservice.entity.ProjectMembers;
import com.wserp.projectservice.entity.enums.ProjectStatus;
import com.wserp.projectservice.exeptions.ConfilictException;
import com.wserp.projectservice.exeptions.NotFoundException;
import com.wserp.projectservice.filter.CurrentUserData;
import com.wserp.projectservice.repository.ProjectMembersRepository;
import com.wserp.projectservice.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMembersRepository projectMembersRepository;
    private final UserServiceClient userServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CurrentUserData currentUserData;

    //-------------------------------------------------------------Project Service Methods---------------------------------------------------------------------

    @Transactional
    public Project createProject(ProjectRequest request) {
        if (projectRepository.findProjectByName(request.getName()).isPresent()) {
            throw new ConfilictException(ErrorMessage.PROJECT_NAME_ALREADY_EXISTS.getMessage());
        }

        Project project = Project.builder()
                .name(request.getName())
                .clientId(currentUserData.getCurrentUserId())
                .description(request.getDescription())
                .status(ProjectStatus.NEW)
                .build();
        project.setCreatedBy(currentUserData.getCurrentUserId());
        project.setUpdatedBy(currentUserData.getCurrentUserId());
        return projectRepository.save(project);
    }


    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));
    }


    @Transactional
    public Project updateProject(String id, UpdateProjectRequest request) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setUpdatedBy(currentUserData.getCurrentUserId());
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(String id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));
        project.setDeleted(true);
        project.setDeletedBy(currentUserData.getCurrentUserId());
        projectRepository.save(project);
    }

    @Transactional
    public Project updateProjectStatus(String id, UpdateStatus updateStatus) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));

        if (updateStatus.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.setStatus(ProjectStatus.IN_PROGRESS);
            project.setStartDate(LocalDate.now());
        } else if (updateStatus.getStatus() == ProjectStatus.COMPLETED) {
            project.setStatus(ProjectStatus.COMPLETED);
            project.setEndDate(LocalDate.now());
        } else {
            project.setStatus(updateStatus.getStatus());
        }
        project.setUpdatedBy(currentUserData.getCurrentUserId());

        return projectRepository.save(project);

    }


    public List<Project> getAllProjects(String clientId, String status) {
        ProjectStatus statusEnum = null;
        if (status != null) {
            try {
                statusEnum = ProjectStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException(ErrorMessage.INVALID_PROJECT_STATUS.getMessage());
            }
        }
        return projectRepository.findProjectsByFilters(clientId, statusEnum);
    }

    public List<Project> getProjectsByClientId(String clientId) {
        return projectRepository.findAllByClientId(clientId);
    }

    //-----------------------------------------------------------Project Members Service Methods----------------------------------------------------------------

    @Transactional
    public ProjectMembers addProjectMembers(String projectId, AddProjectMembers addProjectMembers) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));
        MemberDto memberDto = userServiceClient.getUserById(addProjectMembers.getUserId()).getBody();

        ProjectMembers projectMembers = ProjectMembers.builder()
                .project(project)
                .userId(addProjectMembers.getUserId())
                .role(addProjectMembers.getRole())
                .addedBy(currentUserData.getCurrentUserId())
                .build();

        ProjectMemberEvent event = ProjectMemberEvent.builder()
                .projectTitle(project.getName())
                .userName(memberDto.getName())
                .userEmail(memberDto.getEmail())
                .role(addProjectMembers.getRole().name())
                .addedBy(currentUserData.getCurrentUsername())
                .build();

        kafkaTemplate.send("project-member-topic", event);

        return projectMembersRepository.save(projectMembers);

    }

    public List<ProjectMembers> getProjectMembers(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));
        return projectMembersRepository.findAllByProjectId(project.getId());
    }

    @Transactional
    public void deleteProjectMember(String projectId, String memberId) {
        ProjectMembers projectMembers = projectMembersRepository.findByProjectIdAndUserId(projectId, memberId).orElseThrow(() -> new NotFoundException(ErrorMessage.PROJECT_MEMBER_NOT_FOUND.getMessage()));
        projectMembersRepository.delete(projectMembers);
    }
}
