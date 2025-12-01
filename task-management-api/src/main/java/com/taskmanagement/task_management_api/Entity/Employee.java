package com.taskmanagement.task_management_api.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.engine.internal.Cascade;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id ;
    @NotBlank
    @Column(nullable = false,unique = true)
    String name;
    String role;
    @Column(nullable = false,unique = true)
    String email;

    @OneToMany(mappedBy = "employee",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Task> task = new ArrayList<>();

    public void addTask(Task task1){
        task.add(task1);
        task1.setEmployee(this);
    }

    public void removeTask(Task t){
        task.remove(t);
        t.setEmployee(null);
    }

}
