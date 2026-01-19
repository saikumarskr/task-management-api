package com.taskmanagement.task_management_api.Kafka;

import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskEventProducer {

    private final KafkaTemplate<String, Employee> kafkaTemplate;

    private static final String TOPIC = "task-events";

    public void publish(Employee employee) {
        kafkaTemplate.send(TOPIC, employee);
    }
}