package com.wserp.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String email;
    private String title;
    private String location;
    private String bio;
    private String profileImage;
}
