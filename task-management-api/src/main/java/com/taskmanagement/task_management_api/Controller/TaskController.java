package com.taskmanagement.task_management_api.Controller;

import com.taskmanagement.task_management_api.DTO.TaskRequest;
import com.taskmanagement.task_management_api.DTO.TaskResponse;
import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import com.taskmanagement.task_management_api.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(
        name = "2. Task Management",
        description = "APIs for creating, updating, deleting and querying tasks."
)
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a single task using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskResponse> getById(
            @Parameter(description = "ID of the task", example = "1")
            @PathVariable Long id
    ) {
        TaskResponse taskResponse = taskService.getById(id);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new task",
            description = "Create a new task and assign it to an employee."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request
    ) {
        TaskResponse taskResponse = taskService.create(request);
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a task",
            description = "Update an existing task's details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        TaskResponse taskResponse = taskService.update(id, request);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a task",
            description = "Delete a task by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Long id
    ) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-status")
    @Operation(
            summary = "Get tasks by status",
            description = "Retrieve all tasks filtered by their status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))))
    })
    public ResponseEntity<List<TaskResponse>> getTaskByTaskStatus(
            @Parameter(description = "Task status", example = "IN_PROGRESS")
            @RequestParam TaskStatus taskStatus
    ) {
        List<TaskResponse> taskResponse = taskService.getTasksByStatus(taskStatus);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @GetMapping("/by-priority")
    @Operation(
            summary = "Get tasks by priority",
            description = "Retrieve all tasks filtered by their priority."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))))
    })
    public ResponseEntity<List<TaskResponse>> getTaskByPriority(
            @Parameter(description = "Task priority", example = "HIGH")
            @RequestParam Priority priority
    ) {
        List<TaskResponse> taskResponse = taskService.getTasksByPriority(priority);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update task status",
            description = "Update only the status of an existing task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task status updated",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @Parameter(description = "Task ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "New task status", example = "COMPLETED")
            @RequestParam TaskStatus taskStatus
    ) {
        TaskResponse taskResponse = taskService.updateTaskStatus(id, taskStatus);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search tasks with filters (paginated)",
            description = "Search tasks by optional status and employee ID with pagination support."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @Parameter(description = "Filter by task status", example = "IN_PROGRESS")
            @RequestParam(required = false) TaskStatus status,
            @Parameter(description = "Filter by employee ID", example = "1")
            @RequestParam(required = false) Long employeeId,
            @ParameterObject Pageable pageable
    ) {
        Page<TaskResponse> taskResponse = taskService.getTasks(status, employeeId, pageable);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }
}
