package com.wserp.orgmembersservice.utils;

import com.wserp.common.dto.OrgMembersRequest;
import com.wserp.orgmembersservice.service.OrgMembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = {"org-member-topic"},
        groupId = "org-members-service",
        containerFactory = "kafkaListenerContainerFactory"
)
public class OrgMemberConsumer {

    private final OrgMembersService orgMembersService;


    @KafkaHandler
    public void handleOrgMember(OrgMembersRequest orgMembersRequest, Acknowledgment ack) {
        try {
            orgMembersService.saveOrgMembers(orgMembersRequest);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to handle OrgMembersRequest", e);
            throw e;
        }
    }


}
