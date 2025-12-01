package com.taskmanagement.task_management_api.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.ErrorResponse;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String title;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Priority priority;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
