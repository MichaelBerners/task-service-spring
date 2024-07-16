package ru.belonogov.task_service_spring.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.belonogov.task_service_spring.domain.dto.mapper.EmployeeMapper;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;
import ru.belonogov.task_service_spring.domain.exception.EmployeeNotFoundException;
import ru.belonogov.task_service_spring.domain.repository.EmployeeRepository;
import ru.belonogov.task_service_spring.service.CompanyService;
import ru.belonogov.task_service_spring.service.EmployeeService;
import ru.belonogov.task_service_spring.service.TaskService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final CompanyService companyService;
    private final TaskService taskService;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyService companyService, @Lazy TaskService taskService, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.taskService = taskService;
        this.employeeMapper = employeeMapper;
    }


    @Override
    public EmployeeResponse save(EmployeeRequest employeeRequest) {
        Company company = companyService.findByName(employeeRequest.getCompanyName());
        int defaultRating = 5;
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setRating(defaultRating);
        employee.setCompany(company);
        employee.setTasks(Collections.emptySet());
        Employee save = employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeResponse(save);
    }

    @Override
    public Employee findById(Long employeeId) {

        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("employee not found"));
    }

    @Override
    public List<EmployeeResponse> findAllByCompany(String companyName) {
        List<EmployeeResponse> result = employeeRepository.findByCompanyName(companyName).stream()
                .map($ -> employeeMapper.employeeToEmployeeResponse($))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<EmployeeResponse> findAllByTask(Long taskId) {
        List<Employee> allByTask = employeeRepository.findAllByTask(taskId);
        List<EmployeeResponse> result = allByTask.stream()
                .map($ -> employeeMapper.employeeToEmployeeResponse($))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<Employee> findAllEmployeeByTask(Long taskId) {
        List<Employee> allByTask = employeeRepository.findAllByTask(taskId);
        return allByTask;
    }

    @Override
    public EmployeeResponse update(EmployeeUpdateRequest employeeUpdateRequest) {
        Long id = employeeUpdateRequest.getId();
        Employee employee = findById(id);
        employee.setRating(employeeUpdateRequest.getRating());
        Employee employeeUpdate = employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeResponse(employeeUpdate);
    }

    @Override
    @Transactional
    public EmployeeResponse addNewTask(TaskEmployeeRequest taskEmployeeRequest) {
        Long taskId = taskEmployeeRequest.getTaskId();
        Long employeeId = taskEmployeeRequest.getEmployeeId();
        Task task = taskService.findById(taskId);
        Employee employee = findById(employeeId);
        employee.addTask(task);
        Employee update = employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeResponse(update);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = findById(id);
        taskService.findAllTaskByEmployee(id).stream()
                .peek($ -> employee.removeTask($))
                .collect(Collectors.toSet());
        employeeRepository.delete(employee);
    }
}
