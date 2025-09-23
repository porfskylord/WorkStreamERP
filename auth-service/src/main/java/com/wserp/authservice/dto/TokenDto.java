package com.wserp.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String accessExpirationTime;
    private String refreshExpirationTime;
}
