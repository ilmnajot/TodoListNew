package com.example.todolist.controller;

import com.example.todolist.annotation.CurrentUser;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.response.ApiResponse;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.TaskServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public HttpEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @GetMapping("/completed")
    public HttpEntity<List<Task>> getAllCompletedTask() {
        return ResponseEntity.ok(taskService.findAllCompletedTask());
    }

    @GetMapping("/uncompleted")
    public HttpEntity<List<Task>> getAllUncompletedTask() {
        return ResponseEntity.ok(taskService.findAllUncompledTask());
    }

    @PostMapping("/")
    public HttpEntity<?> createTask(@RequestBody Task task, @CurrentUser User user) {
        return ResponseEntity.ok(taskService.createNewTask(task, user));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        ApiResponse apiResponse =  taskService.updateTask(task, id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTask(@PathVariable Long id) {
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.success ? 200 : 409).body(apiResponse);
    }
    @PutMapping("/{id}/completed")
    public HttpEntity<?> markTaskAsCompleted(@PathVariable Long id){
        ApiResponse apiResponse = taskService.completedTask(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
