package ru.belonogov.task_service_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "/employees",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse employeeResponse = employeeService.save(employeeRequest);

        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/read/company/{companyName}")
    public ResponseEntity<List<EmployeeResponse>> readAllByCompany(@PathVariable String companyName) {
        List<EmployeeResponse> employeesResponse = employeeService.findAllByCompany(companyName);

        return new ResponseEntity<>(employeesResponse, HttpStatus.OK);
    }

    @GetMapping("/read/task/{taskId}")
    public ResponseEntity<List<EmployeeResponse>> readAllByTask(@PathVariable Long taskId) {
        List<EmployeeResponse> employeesResponse = employeeService.findAllByTask(taskId);

        return new ResponseEntity<>(employeesResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<EmployeeResponse> update(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        EmployeeResponse employeeResponse = employeeService.update(employeeUpdateRequest);

        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @PostMapping("/add-task")
    public ResponseEntity<EmployeeResponse> addTask(@RequestBody TaskEmployeeRequest taskEmployeeRequest) {
        EmployeeResponse employeeResponse = employeeService.addNewTask(taskEmployeeRequest);

        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @PostMapping("/delete/{employeeId}")
    public void delete(@PathVariable Long employeeId){
        employeeService.delete(employeeId);
    }
}
