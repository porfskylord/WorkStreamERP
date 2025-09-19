package com.wserp.commondto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberEvent {
    private String projectTitle;
    private String userName;
    private String userEmail;
    private String role;
    private String addedBy;
}
