package ru.belonogov.task_service_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.service.TaskService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.save(taskRequest);

        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }

    @GetMapping("/read/{employeeId}")
    public ResponseEntity<List<TaskResponse>> readAllByEmployee(@PathVariable Long employeeId) {
        List<TaskResponse> tasksResponse = taskService.findAllByEmployee(employeeId);

        return new ResponseEntity<>(tasksResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<TaskResponse> update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        TaskResponse taskResponse = taskService.update(taskUpdateRequest);

        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<TaskResponse> addEmployee(@RequestBody TaskEmployeeRequest taskEmployeeRequest) {
        TaskResponse taskResponse = taskService.addNewEmployeeToTask(taskEmployeeRequest);

        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping("/delete/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }
}
