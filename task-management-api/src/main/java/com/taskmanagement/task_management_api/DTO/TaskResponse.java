package com.taskmanagement.task_management_api.DTO;

import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private TaskStatus status;
    private Priority priority;
    private EmployeeResponse employee;
}
