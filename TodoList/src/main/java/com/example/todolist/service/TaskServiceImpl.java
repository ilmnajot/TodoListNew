package com.example.todolist.service;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.response.ApiResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll(Sort.by("id"));
    }

    @Override
    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue(Sort.by("id"));
    }

    @Override
    public List<Task> findAllUncompledTask() {
        return taskRepository.findByCompletedFalse(Sort.by("id"));
    }


    @Override
    public Task createNewTask(Task task, User user) {
        Optional<Task> optionalTask = taskRepository.findByTask(task.getTask());
        if (optionalTask.isPresent()) {
            return null;
        }
        Task task1 = new Task(
                task.getTask(),
                task.getCompleted(),
                user);
        return taskRepository.save(task1);

    }

    @Override
    public ApiResponse updateTask(Task task, Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task1 = optionalTask.get();
            task1.setId(id);
            task1.setTask(task.getTask());
            task1.setCompleted(task.getCompleted());
            taskRepository.save(task1);
        return new ApiResponse("task has been updated successfully", true);
        }
        return new ApiResponse("there is no such task to update", false);
    }

    @Override
    public ApiResponse deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()){
            taskRepository.deleteById(id);
        return new ApiResponse("task deleted successfully", true);
        }
        return new ApiResponse("task not found to delete", false);
    }

    @Override
    public ApiResponse completedTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setCompleted(true);
            taskRepository.save(task);
            return new ApiResponse("task has been completed successfully", true);
        }
        return new ApiResponse("there is no such task to complete", false);
    }
}
