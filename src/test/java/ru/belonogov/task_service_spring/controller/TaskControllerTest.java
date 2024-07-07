package ru.belonogov.task_service_spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.domain.entity.TaskStatus;
import ru.belonogov.task_service_spring.service.TaskService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
class TaskControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .build();
    }

    @Test
    void create() throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("name");
        taskRequest.setDescription("description");
        taskRequest.setRating(7);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskStatus(TaskStatus.SEARCH_FOR_EMPLOYEES.name());
        taskResponse.setName("name");
        taskResponse.setDescription("description");
        taskResponse.setRating(7);

        when(taskService.save(argThat(arg -> {
            assertThat(arg.getName().equals(taskRequest.getName())).isTrue();
            assertThat(arg.getDescription().equals(taskRequest.getDescription())).isTrue();
            assertThat(arg.getRating() == taskRequest.getRating()).isTrue();
            return true;
        }))).thenReturn(taskResponse);

        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)))
                .andDo(print());
    }

    @Test
    void readAllByEmployee() throws Exception {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskStatus(TaskStatus.SEARCH_FOR_EMPLOYEES.name());
        taskResponse.setName("name");
        taskResponse.setDescription("description");
        taskResponse.setRating(7);
        taskResponseList.add(taskResponse);

        when(taskService.findAllByEmployee(1L)).thenReturn(taskResponseList);

        mockMvc.perform(get("/tasks/read/{employeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponseList)))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());

    }

    @Test
    void update() throws Exception {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(1L);
        taskUpdateRequest.setName("name");
        taskUpdateRequest.setDescription("description");
        taskUpdateRequest.setTaskStatus(TaskStatus.COMPLETED);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskStatus(TaskStatus.SEARCH_FOR_EMPLOYEES.name());
        taskResponse.setName("name");
        taskResponse.setDescription("description");
        taskResponse.setRating(7);

        when(taskService.update(argThat(arg -> {
            assertThat(arg.getId().equals(taskUpdateRequest.getId())).isTrue();
            assertThat(arg.getName().equals(taskUpdateRequest.getName())).isTrue();
            assertThat(arg.getDescription().equals(taskUpdateRequest.getDescription())).isTrue();
            assertThat(arg.getTaskStatus().equals(taskUpdateRequest.getTaskStatus()));
            return true;
        }))).thenReturn(taskResponse);

        mockMvc.perform(post("/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(taskUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)))
                .andDo(print());
    }

    @Test
    void addEmployee() throws Exception {
        TaskEmployeeRequest taskEmployeeRequest = new TaskEmployeeRequest();
        taskEmployeeRequest.setEmployeeId(1L);
        taskEmployeeRequest.setTaskId(3L);

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskStatus(TaskStatus.SEARCH_FOR_EMPLOYEES.name());
        taskResponse.setName("name");
        taskResponse.setDescription("description");
        taskResponse.setRating(7);

        when(taskService.addNewEmployeeToTask(argThat(arg -> {
            assertThat(arg.getTaskId() == taskEmployeeRequest.getTaskId()).isTrue();
            assertThat(arg.getEmployeeId() == taskEmployeeRequest.getEmployeeId()).isTrue();
            return true;
        }))).thenReturn(taskResponse);

        mockMvc.perform(post("/tasks/add-employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(taskEmployeeRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/tasks/delete/{taskId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}