package ru.belonogov.task_service_spring.domain.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String message) {
        super(message);
    }
}
