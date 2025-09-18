package com.wserp.projectservice.dto.request;

import com.wserp.projectservice.entity.enums.Role;
import lombok.Data;

@Data
public class AddProjectMembers {
    private String projectId;
    private String userId;
    private Role role;
}
