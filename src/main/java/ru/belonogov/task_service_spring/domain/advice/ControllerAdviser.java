package ru.belonogov.task_service_spring.domain.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.belonogov.task_service_spring.domain.exception.CompanyNotFoundException;
import ru.belonogov.task_service_spring.domain.exception.EmployeeNotFoundException;
import ru.belonogov.task_service_spring.domain.exception.TaskNotFoundException;

@RestControllerAdvice
public class ControllerAdviser {

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(CompanyNotFoundException e) {

        return "Company not found";
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(EmployeeNotFoundException e) {

        return "e.getMessage()";
    }
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notValid(TaskNotFoundException e) {

        return "Company not found";
    }
}
