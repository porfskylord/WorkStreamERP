package com.wserp.authservice.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class GenericErrorResponse extends  RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public GenericErrorResponse(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
