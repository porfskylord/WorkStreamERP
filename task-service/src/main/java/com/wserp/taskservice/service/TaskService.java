package com.wserp.taskservice.service;

import com.wserp.enums.ErrorMessage;
import com.wserp.taskservice.dto.Comments;
import com.wserp.taskservice.dto.request.CommentRequest;
import com.wserp.taskservice.dto.request.TaskRequest;
import com.wserp.taskservice.dto.request.UpdateStatus;
import com.wserp.taskservice.entity.Task;
import com.wserp.taskservice.entity.enums.TaskStatus;
import com.wserp.taskservice.exceptions.NotFoundException;
import com.wserp.taskservice.filter.CurrentUserData;
import com.wserp.taskservice.repository.TaskAssigneesRepository;
import com.wserp.taskservice.repository.TaskRepository;
import com.wserp.taskservice.repository.TastCommentsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TastCommentsRepository tastCommentsRepository;
    private final TaskAssigneesRepository taskAssigneesRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CurrentUserData currentUserData;

    public Task createTask(@Valid TaskRequest taskRequest) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setProjectId(taskRequest.getProjectId());
        task.setProjectManagerId(currentUserData.getCurrentUserId());
        task.setDeadline(taskRequest.getDeadline());
        task.setPriority(taskRequest.getPriority());
        task.setRate(taskRequest.getRate());
        task.setCreatedBy(currentUserData.getCurrentUserId());
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }

    public Task updateTask(String id, @Valid TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TASK_NOT_FOUND.getMessage()));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setProjectId(taskRequest.getProjectId());
        task.setDeadline(taskRequest.getDeadline());
        task.setPriority(taskRequest.getPriority());
        task.setRate(taskRequest.getRate());
        task.setUpdatedBy(currentUserData.getCurrentUserId());
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(String id, @Valid UpdateStatus updateStatus) {

    }

    public Task getTaskById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TASK_NOT_FOUND.getMessage()));
    }

    public List<Task> getMyTasks(String userId, String role) {
        if (role.equals("CLIENT")) {
            return taskRepository.findByProjectManagerId(userId);
        } else if (role.equals('ADMIN')) {

        } else {
            return taskRepository.findByAssignedTo(userId);
        }
    }

    public List<Task> getAllTasks(String projectId, String status, String clientId, String assignedTo) {
    }

    public void addCommentToTask(String id, @Valid CommentRequest comment) {
    }

    public List<Comments> getCommentsByTaskId(String id) {
    }

    public void updateComment(String id, @Valid CommentRequest comment) {
    }
}
