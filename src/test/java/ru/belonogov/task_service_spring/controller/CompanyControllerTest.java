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
import ru.belonogov.task_service_spring.domain.dto.request.CompanySaveRequest;
import ru.belonogov.task_service_spring.domain.dto.request.CompanyUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.service.CompanyService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(companyController)
                .build();
    }

    @Test
    void test_Create() throws Exception {
        CompanySaveRequest companySaveRequest = new CompanySaveRequest();
        companySaveRequest.setName("Gazprom");

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName("Gazprom");

        when(companyService.create(argThat(arg -> arg.getName().equals(companySaveRequest.getName()))))
                .thenReturn(companyResponse);

        mockMvc.perform(post("/company/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(companySaveRequest)))
                .andExpect(content().json(objectMapper.writeValueAsString(companyResponse)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void test_Read() throws Exception {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName("Gazprom");
        when(companyService.read("Gazprom")).thenReturn(companyResponse);

        mockMvc.perform(get("/company/read/{companyName}", "Gazprom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(companyResponse.getName()))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void update() throws Exception {
        CompanyUpdateRequest companyUpdateRequest = new CompanyUpdateRequest();
        companyUpdateRequest.setId(1L);
        companyUpdateRequest.setName("Lukoil");

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName("Lukoil");

        when(companyService.update(any()))
                .thenReturn(companyResponse);

        mockMvc.perform(post("/company/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(companyUpdateRequest)))
                .andExpect(jsonPath("$.name").value(companyResponse.getName()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/company/delete/{companyId}", 3L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}