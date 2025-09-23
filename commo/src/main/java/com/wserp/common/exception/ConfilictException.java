package com.wserp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConfilictException extends RuntimeException {
    public ConfilictException(String message) {
        super(message);
    }
}
