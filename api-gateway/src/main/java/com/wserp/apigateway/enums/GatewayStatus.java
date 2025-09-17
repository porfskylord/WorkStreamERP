package com.wserp.apigateway.enums;

public enum GatewayStatus {
    BAD_GATEWAY("BAD_GATEWAY"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE"),
    GATEWAY_TIMEOUT("GATEWAY_TIMEOUT");

    private final String status;

    GatewayStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}