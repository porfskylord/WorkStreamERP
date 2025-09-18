package com.wserp.projectservice.dto.request;

import com.wserp.projectservice.entity.enums.ProjectStatus;
import lombok.Data;

@Data
public class UpdateStatus {
    private ProjectStatus status;
}
