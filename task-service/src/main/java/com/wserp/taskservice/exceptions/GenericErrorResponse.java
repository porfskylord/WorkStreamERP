package com.wserp.taskservice.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class GenericErrorResponse extends RuntimeException{
    private final String message;
    private final HttpStatus status;

    public GenericErrorResponse(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
