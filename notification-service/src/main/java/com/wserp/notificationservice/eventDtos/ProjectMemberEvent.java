package com.wserp.notificationservice.eventDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberEvent {
    @JsonProperty("projectTitle")
    private String projectTitle;
    
    @JsonProperty("userName")
    private String userName;
    
    @JsonProperty("userEmail")
    private String userEmail;
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("addedBy")
    private String addedBy;
}
