package com.taskmanagement.task_management_api;

import com.taskmanagement.task_management_api.DTO.TaskResponse;
import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.Task;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import com.taskmanagement.task_management_api.Repository.EmployeeRepository;
import com.taskmanagement.task_management_api.Repository.TaskRepository;
import com.taskmanagement.task_management_api.Service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void getBYId(){
        Employee employee = Employee.builder()
                .id(10L)
                .name("soori")
                .email("soori@gmail.com")
                .build();
        Task task = Task.builder()
                .id(10L)
                .title("Test Task")
                .status(TaskStatus.IN_PROGRESS)
                .priority(Priority.HIGH)
                .employee(employee)
                .build();
        when(taskRepository.findByid(10L)).thenReturn(Optional.of(task));
        TaskResponse taskResponse = taskService.getById(10L);
        assertEquals(10 , taskResponse.getId());

    }
}
