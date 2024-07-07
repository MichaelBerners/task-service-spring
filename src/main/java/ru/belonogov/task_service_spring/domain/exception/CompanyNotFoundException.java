package ru.belonogov.task_service_spring.domain.exception;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
