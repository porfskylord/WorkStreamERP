package com.wserp.projectservice.dto;

import com.wserp.projectservice.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMemberEvent {
    private String projectTitle;
    private String userName;
    private String userEmail;
    private String role;
    private String addedBy;
}
