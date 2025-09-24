package com.wserp.notificationservice.listners;


import com.wserp.common.dto.InviteEvent;
import com.wserp.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(
        topics = {"project-member-topic", "task-assigned-topic", "invite-topic"},
        groupId = "notification-service",
        containerFactory = "kafkaListenerContainerFactory"
)
public class NotificationListener {

    private final EmailService emailService;

    @KafkaHandler
    public void handleInvite(InviteEvent event, Acknowledgment ack) {
        try {
            String subject = "Invite to join the " + event.getOrgName();

            String body = "Hi " + event.getName() + "\n\nYou have been invited to join the organization " + event.getOrgName() + " as " + event.getRole() + " by " + event.getInviteBy() + "\n\nLink: " + event.getInviteUlr() + "\n\nThank You";

            emailService.sendEmail(event.getEmail(), subject, body);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to handle InviteEvent", e);
            throw e;
        }
    }

    @KafkaHandler(isDefault = true)
    public void handleDefault(Object event, Acknowledgment ack) {
        log.warn("Received unknown event type: {}", event);
        ack.acknowledge();
    }
}
