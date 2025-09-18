package com.wserp.projectservice.dto.request;

import com.wserp.projectservice.entity.enums.Role;
import lombok.Data;

@Data
public class ProjectMembersDto {
    private String projectId;
    private String userId;
    private Role role;
}
