package com.wserp.projectservice.dto;

import com.wserp.enums.Role;
import lombok.Data;

@Data
public class ProjectMembersDto {
    private String projectId;
    private String userId;
    private Role role;
}
