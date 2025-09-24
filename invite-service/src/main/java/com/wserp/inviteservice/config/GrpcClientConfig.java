package com.wserp.inviteservice.config;

import com.wserp.common.proto.OrgMemberServiceGrpc;
import com.wserp.common.proto.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.client.orgmember-service.host}")
    private String orgMemberServiceHost;

    @Value("${grpc.client.orgmember-service.port}")
    private int orgMemberServicePort;


    @Value("${grpc.client.user-service.host}")
    private String userServiceHost;

    @Value("${grpc.client.user-service.port}")
    private int userServicePort;

    @Bean
    public ManagedChannel orgMemberServiceChannel() {
        return ManagedChannelBuilder.forAddress(orgMemberServiceHost, orgMemberServicePort)
                .usePlaintext()
                .build();
    }

    @Bean
    public OrgMemberServiceGrpc.OrgMemberServiceBlockingStub orgMemberServiceBlockingStub() {
        return OrgMemberServiceGrpc.newBlockingStub(orgMemberServiceChannel());
    }

    @Bean
    public ManagedChannel userServiceChannel() {
        return ManagedChannelBuilder.forAddress(userServiceHost, userServicePort)
                .usePlaintext()
                .build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
        return UserServiceGrpc.newBlockingStub(userServiceChannel());
    }

}
