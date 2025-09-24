package com.wserp.orgmembersservice.service;

import com.wserp.common.proto.OrgMemberRequest;
import com.wserp.common.proto.OrgMemberResponse;
import com.wserp.common.proto.OrgMemberServiceGrpc;
import com.wserp.orgmembersservice.entity.OrgMembers;
import com.wserp.orgmembersservice.repository.OrgMembersRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class OrgMemberGrpcImpl extends OrgMemberServiceGrpc.OrgMemberServiceImplBase {
    private final OrgMembersRepository orgMembersRepository;

    @Autowired
    public OrgMemberGrpcImpl(OrgMembersRepository orgMembersRepository) {
        this.orgMembersRepository = orgMembersRepository;
    }

    @Override
    public void getOrgMember(OrgMemberRequest request, StreamObserver<OrgMemberResponse> responseObserver) {

        OrgMembers orgMembers = orgMembersRepository.findByOrgIdAndUserId(request.getOrgId(), request.getUserId());

        if (orgMembers == null) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription("Not found").asRuntimeException());
            return;
        }

        OrgMemberResponse response = OrgMemberResponse.newBuilder()
                .setOrgId(orgMembers.getOrgId())
                .setOrgName(orgMembers.getOrgName())
                .setUserId(orgMembers.getUserId())
                .setUserName(orgMembers.getUserName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}