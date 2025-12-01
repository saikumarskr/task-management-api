package com.taskmanagement.task_management_api;

import com.taskmanagement.task_management_api.DTO.EmployeeResponse;
import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Repository.EmployeeRepository;
import com.taskmanagement.task_management_api.Repository.TaskRepository;
import com.taskmanagement.task_management_api.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private EmployeeService employeeService;
    @Test
    void getById_whenEmployeeExists_shouldReturnResponse() {
        Employee employee = Employee.builder()
                .id(1L)
                .name("Sai")
                .email("sai@example.com")
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Sai");
        verify(employeeRepository).findById(1L);
    }


}
