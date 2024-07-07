package ru.belonogov.task_service_spring.service.impl;

import org.springframework.stereotype.Service;
import ru.belonogov.task_service_spring.domain.dto.mapper.TaskMapper;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;
import ru.belonogov.task_service_spring.domain.entity.TaskStatus;
import ru.belonogov.task_service_spring.domain.exception.TaskNotFoundException;
import ru.belonogov.task_service_spring.domain.repository.TaskRepository;
import ru.belonogov.task_service_spring.service.EmployeeService;
import ru.belonogov.task_service_spring.service.TaskService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final EmployeeService employeeService;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, EmployeeService employeeService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeService = employeeService;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskResponse save(TaskRequest taskRequest) {
        TaskStatus newTask = TaskStatus.SEARCH_FOR_EMPLOYEES;
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());
        task.setRating(taskRequest.getRating());
        task.setTaskStatus(newTask);
        task.setEmployees(Collections.emptySet());
        Task save = taskRepository.save(task);

        return taskMapper.taskToTaskResponse(save);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("task not found"));
    }

    @Override
    public Task findByName(String taskName) {

        return  taskRepository.findTaskByName(taskName).orElseThrow(() -> new TaskNotFoundException("task not found"));
    }


    @Override
    public List<TaskResponse> findAllByEmployee(Long employeeId) {
        List<TaskResponse> result = taskRepository.findAllByEmployee(employeeId).stream()
                .map($ -> taskMapper.taskToTaskResponse($))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public TaskResponse update(TaskUpdateRequest taskUpdateRequest) {
        Long id = taskUpdateRequest.getId();
        Task task = findById(id);
        task.setName(taskUpdateRequest.getName());
        task.setDescription(taskUpdateRequest.getDescription());
        task.setTaskStatus(taskUpdateRequest.getTaskStatus());
        Task taskUpdate = taskRepository.save(task);

        return taskMapper.taskToTaskResponse(taskUpdate);
    }

    @Override
    public TaskResponse addNewEmployeeToTask(TaskEmployeeRequest taskEmployeeRequest) {
        Long taskId = taskEmployeeRequest.getTaskId();
        Long employeeId = taskEmployeeRequest.getEmployeeId();
        Employee employee = employeeService.findById(employeeId);
        Task task = findById(taskId);
        task.addEmployee(employee);
        Task update = taskRepository.save(task);

        return taskMapper.taskToTaskResponse(update);
    }

    @Override
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}
