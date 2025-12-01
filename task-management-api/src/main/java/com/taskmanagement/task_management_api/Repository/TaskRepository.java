package com.taskmanagement.task_management_api.Repository;
import com.taskmanagement.task_management_api.Entity.Priority;
import com.taskmanagement.task_management_api.Entity.Task;
import com.taskmanagement.task_management_api.Entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findByid(Long id);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(Priority priority);

    Page<Task> findByStatusAndEmployee_Id(TaskStatus status, Long employeeId, Pageable pageable);

    Page<Task> findByEmployee_Id(Long employeeId, Pageable pageable);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    List<Task> findByEmployee_Id(Long id);

    List<Task> findByEmployeeIdAndStatus(Long employeeId, TaskStatus status);
}
