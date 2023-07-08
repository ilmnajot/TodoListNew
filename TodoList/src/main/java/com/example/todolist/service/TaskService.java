package com.example.todolist.service;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.response.ApiResponse;

import java.util.List;

public interface TaskService {
    List<Task> getAllTask();

    List<Task> findAllCompletedTask();

    List<Task> findAllUncompledTask();

    Task createNewTask(Task task, User user);

    ApiResponse updateTask(Task task, Long id);

    ApiResponse deleteTask(Long id);

    ApiResponse completedTask(Long id);
}
