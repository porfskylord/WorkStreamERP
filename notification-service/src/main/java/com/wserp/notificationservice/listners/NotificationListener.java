package com.wserp.notificationservice.listners;



import com.wserp.commondto.ProjectMemberEvent;
import com.wserp.commondto.TaskAssignedEvent;
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
        topics = {"project-member-topic", "task-assigned-topic"},
        groupId = "notification-service",
        containerFactory = "kafkaListenerContainerFactory"
)
public class NotificationListener {

    private final EmailService emailService;

    @KafkaHandler
    public void handleProjectMember(ProjectMemberEvent event, Acknowledgment ack) {
        try {
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
            log.error("Failed to handle ProjectMemberEvent", e);
            throw e;
        }
    }

    @KafkaHandler
    public void handleTaskAssigned(TaskAssignedEvent event, Acknowledgment ack) {
        try {
            String subject = "Task Assigned";
            String body = String.format(
                    "Hi %s,%n%nYou have been assigned a new task: \"%s\" in project \"%s\".%n%nAssigned by: %s",
                    event.getUserName(),
                    event.getTaskTitle(),
                    event.getProjectTitle(),
                    event.getAssignedBy()
            );
            emailService.sendEmail(event.getUserEmail(), subject, body);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to handle TaskAssignedEvent", e);
            throw e;
        }
    }

    @KafkaHandler(isDefault = true)
    public void handleDefault(Object event, Acknowledgment ack) {
        log.warn("Received unknown event type: {}", event);
        ack.acknowledge();
    }
}
