package ru.belonogov.task_service_spring.service;

import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.domain.entity.Task;

import java.util.List;

public interface TaskService {

    TaskResponse save(TaskRequest taskRequest);

    Task findById(Long id);

    Task findByName(String taskName);

    List<TaskResponse> findAllByEmployee(Long id);

    List<Task> findAllTaskByEmployee(Long id);

    TaskResponse update(TaskUpdateRequest taskUpdateRequest);

    TaskResponse addNewEmployeeToTask(TaskEmployeeRequest taskEmployeeRequest);

    void delete (Long id);
}
