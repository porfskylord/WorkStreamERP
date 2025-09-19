package com.wserp.notificationservice.listners;

import com.wserp.notificationservice.eventDtos.ProjectMemberEvent;
import com.wserp.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListner {

    private final EmailService emailService;

    @KafkaListener(
        topics = "project-member-topic", 
        groupId = "notification-service",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(
        @Payload(required = false) ProjectMemberEvent event,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(KafkaHeaders.OFFSET) long offset,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        Acknowledgment ack
    ) {
        try {
            if (event == null) {
                return;
            }
            String subject = "Project Member Added";
            String body = String.format(
                "Hi %s,%n%nYou have been added as a member to the project \"%s\" as %s.%n%nAdded by: %s",
                event.getUserName() != null ? event.getUserName() : "there",
                event.getProjectTitle(),
                event.getRole(),
                event.getAddedBy()
            );

            emailService.sendEmail(event.getUserEmail(), subject, body);
            ack.acknowledge();
            
        } catch (Exception e) {
            log.error(" Failed to process message from topic: {}, partition: {}, offset: {}. Error: {}",
                     topic, partition, offset, e.getMessage(), e);
            throw e;
        }
    }
}
