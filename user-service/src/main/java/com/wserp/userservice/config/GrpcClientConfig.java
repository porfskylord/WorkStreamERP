package com.wserp.userservice.config;

import com.wserp.common.proto.OrganizationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.client.organization-service.host}")
    private String organizationServiceHost;

    @Value("${grpc.client.organization-service.port}")
    private int organizationServicePort;

    @Bean
    public ManagedChannel organizationServiceChannel() {
        return ManagedChannelBuilder.forAddress(organizationServiceHost, organizationServicePort)
                .usePlaintext()
                .build();
    }

    @Bean
    public OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub() {
        return OrganizationServiceGrpc.newBlockingStub(organizationServiceChannel());
    }
}
