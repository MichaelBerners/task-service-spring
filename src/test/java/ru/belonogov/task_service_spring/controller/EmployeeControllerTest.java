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
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.request.EmployeeUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.request.TaskEmployeeRequest;
import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.service.EmployeeService;

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
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .build();
    }

    @Test
    void testCreate() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("Ivan");
        employeeRequest.setLastName("Ivanov");
        employeeRequest.setCompanyName("Lukoil");

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setFirstName("Ivan");
        employeeResponse.setLastName("Ivanov");
        employeeResponse.setCompanyName("Lukoil");
        employeeResponse.setRating(5);

        when(employeeService.save(argThat(arg -> {
            assertThat(arg.getFirstName().equals(employeeRequest.getFirstName())).isTrue();
            assertThat(arg.getLastName().equals(employeeRequest.getLastName())).isTrue();
            assertThat(arg.getCompanyName().equals(employeeRequest.getCompanyName())).isTrue();
            return true;
        }))).thenReturn(employeeResponse);

        mockMvc.perform(post("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponse)))
                .andDo(print());

    }

    @Test
    void testReadAllByCompany() throws Exception {
        EmployeeResponse employee = new EmployeeResponse();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setRating(5);
        employee.setCompanyName("Gazprom");
        List<EmployeeResponse> employeesResponse = new ArrayList<>();
        employeesResponse.add(employee);
        when(employeeService.findAllByCompany("Gazprom")).thenReturn(employeesResponse);


        mockMvc.perform(get("/employees/read/company/{companyName}", "Gazprom")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].companyName").value("Gazprom"))
                .andDo(print());
    }

    @Test
    void testReadAllByTask() throws Exception {
        List<EmployeeResponse> employeesResponse = new ArrayList<>();
        EmployeeResponse employee = new EmployeeResponse();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setRating(5);
        employee.setCompanyName("Gazprom");
        employeesResponse.add(employee);

        when(employeeService.findAllByTask(1L)).thenReturn(employeesResponse);

        mockMvc.perform(get("/employees/read/task/{taskId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(employeesResponse)))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest();
        employeeUpdateRequest.setId(1L);
        employeeUpdateRequest.setRating(7);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setFirstName("firstName");
        employeeResponse.setLastName("lastName");
        employeeResponse.setRating(7);
        employeeResponse.setCompanyName("Gazprom");

        when(employeeService.update(argThat(arg -> {
            assertThat(arg.getId() == employeeUpdateRequest.getId()).isTrue();
            assertThat(arg.getRating() == employeeUpdateRequest.getRating()).isTrue();
            return true;
        }))).thenReturn(employeeResponse);

        mockMvc.perform(post("/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(employeeUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponse)))
                .andDo(print());

    }

    @Test
    void testAddTask() throws Exception {
        TaskEmployeeRequest taskEmployeeRequest = new TaskEmployeeRequest();
        taskEmployeeRequest.setEmployeeId(1L);
        taskEmployeeRequest.setTaskId(3L);

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setFirstName("firstName");
        employeeResponse.setLastName("lastName");
        employeeResponse.setRating(7);
        employeeResponse.setCompanyName("Gazprom");

        when(employeeService.addNewTask(argThat(arg -> {
            assertThat(arg.getEmployeeId() == taskEmployeeRequest.getEmployeeId()).isTrue();
            assertThat(arg.getTaskId() == taskEmployeeRequest.getTaskId()).isTrue();
            return true;
        }))).thenReturn(employeeResponse);

        mockMvc.perform(post("/employees/add-task")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(taskEmployeeRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(employeeResponse)))
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(post("/employees/delete/{employeeId}", 1L).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}