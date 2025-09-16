package com.wserp.userservice.entity.enums;

public enum ErrorMessage {
    USER_NOT_FOUND("User not found"),
    EMAIL_ALREADY_EXISTS("Email already in use"),
    INVALID_CREDENTIALS("Invalid credentials"),
    UNAUTHORIZED_ACCESS("Unauthorized access");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}