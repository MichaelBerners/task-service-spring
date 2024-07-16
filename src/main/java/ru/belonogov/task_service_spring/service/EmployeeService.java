package ru.belonogov.task_service_spring.service;

import ru.belonogov.task_service_spring.domain.dto.request.EmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse save(EmployeeRequest employeeRequest);

    Employee findById(Long id);

    List<EmployeeResponse> findAllByCompany(String companyName);

    List<EmployeeResponse> findAllByTask(Long taskId);

    List<Employee> findAllEmployeeByTask(Long taskId);

    EmployeeResponse update(EmployeeUpdateRequest employeeUpdateRequest);

    EmployeeResponse addNewTask(TaskEmployeeRequest taskEmployeeRequest);

    void delete (Long id);
}
