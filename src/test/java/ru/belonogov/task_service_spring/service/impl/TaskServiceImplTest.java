package ru.belonogov.task_service_spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private TaskServiceImpl taskService;
    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @Test
    void testSave_shouldTaskDTO_whenTaskSaved() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Новое задание");
        taskRequest.setDescription("Описание нового задания");
        taskRequest.setRating(7);
        Task task = new Task();
        TaskResponse taskResponse = new TaskResponse();
        when(taskRepository.save(argThat(arg -> {
            assertThat(arg.getName().equals("Новое задание")).isTrue();
            assertThat(arg.getDescription().equals("Описание нового задания")).isTrue();
            assertThat(arg.getRating() == 7).isTrue();
            assertThat(arg.getTaskStatus() == TaskStatus.SEARCH_FOR_EMPLOYEES).isTrue();
            return true;
        }))).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse result = taskService.save(taskRequest);

        assertThat(result).isEqualTo(taskResponse);
        verify(taskRepository).save(any());
        verify(taskMapper).taskToTaskResponse(task);
    }

    @Test
    void testFindById_shouldReturnTaskDTO_whenTaskExist() {
        Long id = 1L;
        Task task = new Task();
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Task result = taskService.findById(id);

        assertThat(result).isEqualTo(task);
        verify(taskRepository).findById(id);
    }

    @Test
    void testFindById_shouldReturnTaskNotFoundException_whenTaskIsNotExist() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(id));

        verify(taskRepository).findById(id);
    }

    @Test
    void testFindAllByEmployee_shouldReturnListTaskDAO_whenEmployeeExist() {
        Long id = 1L;
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = List.of(task1, task2);
        TaskResponse taskResponse = new TaskResponse();
        when(taskRepository.findAllByEmployee(id)).thenReturn(tasks);
        when(taskMapper.taskToTaskResponse(taskArgumentCaptor.capture())).thenReturn(taskResponse);

        List<TaskResponse> result = taskService.findAllByEmployee(id);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(taskArgumentCaptor.getAllValues().contains(task1)).isTrue();
        assertThat(taskArgumentCaptor.getAllValues().contains(task2)).isTrue();
        verify(taskRepository).findAllByEmployee(id);
        verify(taskMapper, times(2)).taskToTaskResponse(any());
    }

    @Test
    void testFindAllByEmployee_shouldReturnEmptyList_whenEmployeeIsNotExist() {
        Long id = 1L;
        when(taskRepository.findAllByEmployee(id)).thenReturn(Collections.emptyList());

        List<TaskResponse> result = taskService.findAllByEmployee(id);

        assertThat(result).isEmpty();
        verify(taskRepository).findAllByEmployee(id);
    }

    @Test
    void testUpdate_shouldReturnTaskDTO_whenTaskExist() {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(1L);
        taskUpdateRequest.setName("Старое название");
        taskUpdateRequest.setDescription("Новое описание");
        taskUpdateRequest.setTaskStatus(TaskStatus.IN_PROGRESS);
        Task task = new Task();
        task.setId(taskUpdateRequest.getId());
        TaskResponse taskResponse = new TaskResponse();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse result = taskService.update(taskUpdateRequest);

        assertThat(result).isEqualTo(taskResponse);
        assertThat(taskArgumentCaptor.getValue())
                .matches(e -> e.getId() == taskUpdateRequest.getId())
                .matches(e -> e. getName().equals(taskUpdateRequest.getName()))
                .matches(e -> e.getDescription().equals(taskUpdateRequest.getDescription()))
                .matches(e -> e.getTaskStatus() == taskUpdateRequest.getTaskStatus());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any());
        verify(taskMapper).taskToTaskResponse(task);
    }


    @Test
    void testAddNewEmployee_whenEmployeeAndTaskExist() {
        TaskEmployeeRequest taskEmployeeRequest = new TaskEmployeeRequest();
        taskEmployeeRequest.setTaskId(1L);
        taskEmployeeRequest.setEmployeeId(2L);
        Task task = new Task();
        task.setEmployees(new HashSet<>());
        Employee employee = new Employee();
        employee.setTasks(new HashSet<>());
        TaskResponse taskResponse = new TaskResponse();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(employeeService.findById(2L)).thenReturn(employee);
        when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse result = taskService.addNewEmployeeToTask(taskEmployeeRequest);

        assertThat(taskArgumentCaptor.getValue())
                .matches(e -> e.getEmployees().contains(employee));
        verify(taskRepository).findById(1L);
        verify(employeeService).findById(2L);
        verify(taskRepository).save(task);
    }


    @Test
    void testDelete_shouldReturnTrue_whenEmployeeExist() {
        Long id = 1L;
        Task task = new Task();
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        taskService.delete(id);

        verify(taskRepository).findById(id);
        verify(taskRepository).delete(task);
    }

}