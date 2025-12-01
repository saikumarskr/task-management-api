package com.taskmanagement.task_management_api.Service;

import com.taskmanagement.task_management_api.DTO.AuthRequest;
import com.taskmanagement.task_management_api.DTO.AuthResponse;
import com.taskmanagement.task_management_api.Entity.User;
import com.taskmanagement.task_management_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse addUser(AuthRequest authRequest){
        User user =User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(authRequest.getRole())
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
