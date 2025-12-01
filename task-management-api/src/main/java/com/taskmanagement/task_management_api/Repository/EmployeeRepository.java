package com.taskmanagement.task_management_api.Repository;

import com.taskmanagement.task_management_api.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findById(Long id);
}
