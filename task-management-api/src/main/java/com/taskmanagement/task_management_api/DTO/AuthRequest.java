package com.taskmanagement.task_management_api.DTO;

import com.taskmanagement.task_management_api.Entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role=Role.ADMIN;
}
