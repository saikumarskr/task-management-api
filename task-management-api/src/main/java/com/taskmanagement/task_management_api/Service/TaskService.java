package com.taskmanagement.task_management_api.Service;

import com.taskmanagement.task_management_api.DTO.EmployeeResponse;
import com.taskmanagement.task_management_api.DTO.TaskRequest;
import com.taskmanagement.task_management_api.DTO.TaskResponse;
import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.Task;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import com.taskmanagement.task_management_api.Exception.EmployeeNotFoundException;
import com.taskmanagement.task_management_api.Exception.TaskNotFoundException;
import com.taskmanagement.task_management_api.Kafka.TaskEventProducer;
import com.taskmanagement.task_management_api.Repository.EmployeeRepository;
import com.taskmanagement.task_management_api.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    public JavaMailSender javaMailSender;


    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskEventProducer taskEventProducer;

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskEventProducer taskEventProducer) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskEventProducer = taskEventProducer;
    }


    public TaskResponse getById(Long id)  {
        Task task = taskRepository.findByid(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        return toResponse(task);
    }

    public Page<TaskResponse> getTasks(TaskStatus status, Long employeeId, Pageable pageable) {
        Page<Task> tasks;

        if (status != null && employeeId != null) {
            tasks = taskRepository.findByStatusAndEmployee_Id(status, employeeId, pageable);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);

        } else if (employeeId != null) {
            tasks = taskRepository.findByEmployee_Id(employeeId, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }
        return tasks.map(this::toResponse);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TaskResponse create(TaskRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        Task task = Task.builder()
                .title(request.getTitle())
                .status(request.getStatus())
                .priority(request.getPriority())
                .employee(employee)
                .build();

        employee.addTask(task);
        Task saved = taskRepository.save(task);
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(employee.getEmail());
            simpleMailMessage.setSubject("New Task Assign");
            simpleMailMessage.setText(employee.getTask().toString());
            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return toResponse(saved);
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest request)  {
        Task task = taskRepository.findByid(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setEmployee(employee);

        return toResponse(task);
    }

    @Transactional
    public void delete(Long id) {
        Task task = taskRepository.findByid(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        Employee employee = task.getEmployee();
        if (employee != null) {
            employee.removeTask(task); // break relation
        }
        taskRepository.delete(task); // delete record
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findByid(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        if(status==TaskStatus.DONE){
            taskEventProducer.publish(task.getEmployee());
        }
        task.setStatus(status);
        return toResponse(task);
    }

    // helper function

    private TaskResponse toResponse(Task task) {
        Employee employee = task.getEmployee();
        EmployeeResponse employeeResponse = null;
        if (employee != null) {
            employeeResponse = EmployeeResponse.builder()
                    .id(employee.getId())
                    .name(employee.getName())
                    .email(employee.getEmail())
                    .role(employee.getRole())
                    .build();
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
