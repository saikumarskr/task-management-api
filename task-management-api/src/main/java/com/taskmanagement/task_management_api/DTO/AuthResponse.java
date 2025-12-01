package com.taskmanagement.task_management_api.DTO;

import com.taskmanagement.task_management_api.Entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
}
