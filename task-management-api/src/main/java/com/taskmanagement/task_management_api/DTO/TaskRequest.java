package com.taskmanagement.task_management_api.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotNull(message = "Status must not be null")
    private TaskStatus status;

    @NotNull(message = "Priority must not be null")
    private Priority priority;

    @NotNull(message = "Employee id must not be null")
    @Min(1L)
    private Long employeeId;
}
