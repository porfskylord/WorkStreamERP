package com.wserp.common.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {
    private Map<String, String> validationErrors;
}
