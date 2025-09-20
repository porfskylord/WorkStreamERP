package com.wserp.taskservice.controller;

import com.wserp.taskservice.dto.Comments;
import com.wserp.taskservice.dto.TaskDto;
import com.wserp.taskservice.dto.request.CommentRequest;
import com.wserp.taskservice.dto.request.TaskRequest;
import com.wserp.taskservice.dto.request.UpdateStatus;
import com.wserp.taskservice.filter.CurrentUserData;
import com.wserp.taskservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private CurrentUserData currentUserData;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.ok(modelMapper.map(taskService.createTask(taskRequest), TaskDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(taskService.getTaskById(id), TaskDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String id, @RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.ok(modelMapper.map(taskService.updateTask(id, taskRequest), TaskDto.class));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable String id, @RequestBody @Valid UpdateStatus updateStatus) {
        taskService.updateTaskStatus(id, updateStatus);
        return ResponseEntity.ok("Task status updated successfully");
    }


    @GetMapping("/mytasks")
    public ResponseEntity<List<TaskDto>> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks(currentUserData.getCurrentUserId(), currentUserData.getCurrentUserRole()).stream()
                .map(task -> modelMapper.map(task, TaskDto.class)).toList());
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestParam(required = false) String projectId
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String clientId
            , @RequestParam(required = false) String assignedTo) {
        return ResponseEntity.ok(taskService.getAllTasks(projectId, status, clientId, assignedTo).stream()
                .map(task -> modelMapper.map(task, TaskDto.class)).toList());

    }

//-------------------------------------------------Comments--------------------------------------------------------------------------

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> addCommentToTask(@PathVariable String id, @RequestBody @Valid CommentRequest
            comment) {
        taskService.addCommentToTask(id, comment);
        return ResponseEntity.ok("Comment added successfully");
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<String> updateComment(@PathVariable String id, @RequestBody @Valid CommentRequest comment) {
        taskService.updateComment(id, comment);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comments>> getCommentsByTaskId(@PathVariable String id) {
        return ResponseEntity.ok(taskService.getCommentsByTaskId(id));
    }

}
