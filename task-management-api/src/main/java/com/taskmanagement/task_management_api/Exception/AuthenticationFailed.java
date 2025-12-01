package com.taskmanagement.task_management_api.Exception;

public class AuthenticationFailed extends RuntimeException{
    public AuthenticationFailed(Exception message) {
        super(message);
    }
}
