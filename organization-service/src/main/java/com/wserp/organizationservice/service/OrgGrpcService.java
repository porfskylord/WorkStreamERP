package com.wserp.organizationservice.service;

import com.wserp.common.proto.OrganizationRequest;
import com.wserp.common.proto.OrganizationResponse;
import com.wserp.common.proto.OrganizationServiceGrpc;
import com.wserp.organizationservice.entity.Organization;
import com.wserp.organizationservice.repository.OrganizationRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;

@GrpcService
@RequiredArgsConstructor
public class OrgGrpcService extends OrganizationServiceGrpc.OrganizationServiceImplBase {

    private final OrganizationRepository organizationRepository;
    @Value("${app.system-user-id}")
    private String systemUserId;


    @Override
    @Transactional
    public void createOrganization(OrganizationRequest request, StreamObserver<OrganizationResponse> responseObserver) {
        if (organizationRepository.findByName(request.getName()).isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Organization already exists")
                            .asException()
            );
            return;
        }

        Organization organization = Organization.builder()
                .name(request.getName())
                .ownerId(request.getOwnerId())
                .build();
        organization.setCreatedBy(systemUserId);
        organization.setUpdatedBy(systemUserId);

        Organization savedOrganization = organizationRepository.save(organization);

        OrganizationResponse response = OrganizationResponse.newBuilder()
                .setId(savedOrganization.getId())
                .setName(savedOrganization.getName())
                .setOwnerId(savedOrganization.getOwnerId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
