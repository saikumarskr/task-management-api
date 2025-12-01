package com.taskmanagement.task_management_api.Controller;

import com.taskmanagement.task_management_api.DTO.EmployeeRequest;
import com.taskmanagement.task_management_api.DTO.EmployeeResponse;
import com.taskmanagement.task_management_api.DTO.TaskResponse;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import com.taskmanagement.task_management_api.Service.EmployeeService;
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
@RequestMapping("/api/employees")
@Tag(
        name = "1. Employee Management",
        description = "APIs for managing employees and viewing their assigned tasks."
)
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get employee by ID",
            description = "Fetch a single employee's details using their unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponse> getById(
            @Parameter(description = "ID of the employee to be retrieved", example = "1")
            @PathVariable long id
    ) {
        EmployeeResponse employeeResponse = employeeService.getById(id);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Get all employees (paginated)",
            description = "Retrieve a paginated list of employees."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<EmployeeResponse>> getAll(
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(employeeService.find(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('READ')")
    @Operation(
            summary = "Create a new employee",
            description = "Add a new employee to the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<EmployeeResponse> addEmployee(
            @Valid @RequestBody EmployeeRequest employeeRequest
    ) {
        EmployeeResponse employeeResponse = employeeService.create(employeeRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an employee",
            description = "Update the details of an existing employee by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Employee updated",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @Parameter(description = "ID of the employee to be updated", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request
    ) {
        EmployeeResponse employeeResponse = employeeService.Update(id, request);
        return new ResponseEntity<>(employeeResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    @Operation(
            summary = "Delete an employee",
            description = "Delete an employee by their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to be deleted", example = "1")
            @PathVariable Long id
    ) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    @Operation(
            summary = "Get all tasks for an employee",
            description = "Retrieve all tasks assigned to a specific employee."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<List<TaskResponse>> findAllTasks(
            @Parameter(description = "Employee ID", example = "1")
            @PathVariable Long id
    ) {
        List<TaskResponse> taskResponses = employeeService.getAllTasksOfEmployee(id);
        return new ResponseEntity<>(taskResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}/tasks/by-status")
    @Operation(
            summary = "Get employee tasks by status",
            description = "Retrieve all tasks for an employee filtered by task status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
            @Parameter(description = "Employee ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Task status to filter by", example = "IN_PROGRESS")
            @RequestParam TaskStatus taskStatus
    ) {
        List<TaskResponse> taskResponses = employeeService.getEmployeeTasksByStatus(id, taskStatus);
        return new ResponseEntity<>(taskResponses, HttpStatus.OK);
    }
}
