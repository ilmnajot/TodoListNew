package com.example.todolist.service;

import com.example.todolist.payload.UserDTO;
import com.example.todolist.response.ApiResponse;

public interface UserService {
    ApiResponse registerUser(UserDTO userDTO);
}
