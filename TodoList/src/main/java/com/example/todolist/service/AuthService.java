package com.example.todolist.service;

import com.example.todolist.entity.User;
import com.example.todolist.enums.RoleName;
import com.example.todolist.payload.UserDTO;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.response.ApiResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("username not found"));
    }


    public ApiResponse registerUser(UserDTO userDTO) {
        boolean username = userRepository.existsByUsername(userDTO.getUsername());
        if (username) {
            return new ApiResponse("this username is already is taken", false);
        }
        User user = new User(
                userDTO.getName(),
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                RoleName.USER

        );
        userRepository.save(user);
        return new ApiResponse("user registered successfully", true);
    }
}
