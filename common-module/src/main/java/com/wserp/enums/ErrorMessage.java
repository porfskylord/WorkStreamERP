package com.wserp.enums;

public enum ErrorMessage {
    USER_NOT_FOUND("User not found"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    EMAIL_ALREADY_EXISTS("Email already in use"),
    INVALID_CREDENTIALS("Invalid credentials"),
    UNAUTHORIZED_ACCESS("Unauthorized access"),
    PROJECT_NAME_ALREADY_EXISTS("Project name already exists"),
    INVALID_PROJECT_STATUS("Invalid project status value"),
    PROJECT_MEMBER_NOT_FOUND("Project member not found"),
    PROJECT_NOT_FOUND("Project not found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}