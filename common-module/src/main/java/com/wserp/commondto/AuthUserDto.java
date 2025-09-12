package com.wserp.commondto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDto {
    private Long id;
    private String email;
    private String password;
    private String role;
}
