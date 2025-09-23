package com.wserp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongInternalToken extends RuntimeException {
    public WrongInternalToken(String message) {
        super(message);
    }
}
