package com.taskmanagement.task_management_api.Service;

import com.taskmanagement.task_management_api.DTO.EmployeeRequest;
import com.taskmanagement.task_management_api.DTO.EmployeeResponse;
import com.taskmanagement.task_management_api.DTO.TaskResponse;
import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Entity.Task;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import com.taskmanagement.task_management_api.Exception.EmployeeNotFoundException;
import com.taskmanagement.task_management_api.Kafka.TaskEventProducer;
import com.taskmanagement.task_management_api.Repository.EmployeeRepository;
import com.taskmanagement.task_management_api.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public Page<EmployeeResponse> find(Pageable pageable){
        return employeeRepository.findAll(pageable)
                .map(this::toResponse);
    }
    public EmployeeResponse getById(Long id) {
        Employee employee =findIfEmployExist(id);
        return toResponse(employee);
    }
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        Employee saved = employeeRepository.save(employee);
        return toResponse(saved);
    }
    @Transactional( propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public EmployeeResponse Update(Long id, EmployeeRequest request) {
        Employee employee =findIfEmployExist(id);
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setRole(request.getRole());
        return toResponse(employee);
    }
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public List<TaskResponse> getAllTasksOfEmployee(Long id){
        findIfEmployExist(id);
        List<Task> tasks =taskRepository.findByEmployee_Id(id);
        return tasks.stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getEmployeeTasksByStatus(Long id, TaskStatus status) {

        findIfEmployExist(id);

        return taskRepository.findByEmployeeIdAndStatus(id, status)
                .stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }


    //  Helper functions
    private Employee findIfEmployExist(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }
    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .build();
    }
    private TaskResponse toTaskResponse(Task task) {
        Employee employee = task.getEmployee();
        EmployeeResponse employeeResponse = null;
        if (employee != null) {
            employeeResponse = toResponse(employee);
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .priority(task.getPriority())
                .employee(employeeResponse)
                .build();
    }

}
