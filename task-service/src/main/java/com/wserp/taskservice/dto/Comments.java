package com.wserp.taskservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comments {
    private String id;
    private String comment;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
