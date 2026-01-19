package com.taskmanagement.task_management_api.Kafka;

import com.taskmanagement.task_management_api.Entity.Employee;
import com.taskmanagement.task_management_api.Entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskEventConsumer {

    @KafkaListener(
            topics = "task-events",
            groupId = "task-management-group"
    )
    public void consume(Employee employee) {
        log.info("Kafka Event Received: {}", employee);
    }
}
