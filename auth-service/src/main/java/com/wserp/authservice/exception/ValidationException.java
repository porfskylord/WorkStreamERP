package com.wserp.authservice.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ValidationException extends RuntimeException {
    private Map<String, String> validationErrors;
}
