package com.wserp.projectservice.dto.request;

import com.wserp.projectservice.entity.enums.Role;
import lombok.Data;

@Data
public class AddProjectMembers {
    private String userName;
    private Role role;
}
