package ru.belonogov.task_service_spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.belonogov.task_service_spring.service.TaskService;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CompanyService companyService;
    @Mock
    private TaskService taskService;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;


    @Test
    void testSave_shouldReturnEmployeeDAO_whenCompanyExist() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("Igor");
        employeeRequest.setLastName("Nikitin");
        employeeRequest.setCompanyName("Gazprom");
        Company company = new Company();
        company.setName(employeeRequest.getCompanyName());
        Employee employee = new Employee();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(companyService.findByName("Gazprom")).thenReturn(company);
        when(employeeRepository.save(employeeArgumentCaptor.capture())).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.save(employeeRequest);

        assertThat(result).isEqualTo(employeeResponse);
        assertThat(employeeArgumentCaptor.getValue())
                .matches(e -> e.getFirstName().equals(employeeRequest.getFirstName()))
                .matches(e -> e.getLastName().equals(employeeRequest.getLastName()))
                .matches(e -> e.getRating() == 5)
                .matches(e -> e.getTasks().isEmpty())
                .matches(e -> e.getCompany().getName().equals(employeeRequest.getCompanyName()));
        verify(companyService).findByName("Gazprom");
        verify(employeeRepository).save(any());
        verify(employeeMapper).employeeToEmployeeResponse(employee);

    }

    @Test
    void testFindById_shouldReturnEmployeeDTO_whenEmployeeExist() {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findById(id);

        assertThat(result).isEqualTo(employee);
        verify(employeeRepository).findById(id);
    }

    @Test
    void testFindById_shouldReturnEmployeeNotFoundException_whenEmployeeIsNotExist() {
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findById(id));
    }

    @Test//?
    void testFindAllByCompany_shouldListEmployeeDAO_whenCompanyExist() {
        String companyName = "Gazprom";
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employees = List.of(employee1, employee2);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeRepository.findByCompanyName(companyName)).thenReturn(employees);
        when(employeeMapper.employeeToEmployeeResponse(employeeArgumentCaptor.capture())).thenReturn(employeeResponse);

        List<EmployeeResponse> result = employeeService.findAllByCompany(companyName);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(employeeArgumentCaptor.getAllValues().contains(employee1)).isTrue();
        assertThat(employeeArgumentCaptor.getAllValues().contains(employee2)).isTrue();
        verify(employeeRepository).findByCompanyName(companyName);
        verify(employeeMapper, times(2)).employeeToEmployeeResponse(any());
    }

    @Test//?
    void testFindAllByTask_shouldListEmployeeDAO_whenTaskExist() {
        Long taskId = 3L;
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employees = List.of(employee1, employee2);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeRepository.findAllByTask(taskId)).thenReturn(employees);
        when(employeeMapper.employeeToEmployeeResponse(employeeArgumentCaptor.capture())).thenReturn(employeeResponse);

        List<EmployeeResponse> result = employeeService.findAllByTask(taskId);

        assertThat(result)
                .isNotNull()
                .isNotEmpty();
        assertThat(employeeArgumentCaptor.getAllValues().contains(employee1)).isTrue();
        assertThat(employeeArgumentCaptor.getAllValues().contains(employee2)).isTrue();
        verify(employeeRepository).findAllByTask(taskId);
        verify(employeeMapper, times(2)).employeeToEmployeeResponse(any());
    }

    @Test
    void testFindAllByTask_shouldEmptyList_whenTaskIsNotExist() {
        Long taskId = 5L;
        when(employeeRepository.findAllByTask(taskId)).thenReturn(Collections.emptyList());

        List<EmployeeResponse> result = employeeService.findAllByTask(taskId);

        assertThat(result).isEmpty();
        verify(employeeRepository).findAllByTask(taskId);
    }

    @Test
    void testUpdate_shouldEmployeeDTO_whenEmployeeExist() {
        EmployeeUpdateRequest employeeUpdateRequest = new EmployeeUpdateRequest();
        employeeUpdateRequest.setId(1L);
        employeeUpdateRequest.setRating(7);
        Employee employee = new Employee();
        employee.setId(1L);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeRepository.findById(employeeUpdateRequest.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employeeArgumentCaptor.capture())).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(employeeResponse);

        EmployeeResponse result = employeeService.update(employeeUpdateRequest);

        assertThat(result).isEqualTo(employeeResponse);
        assertThat(employeeArgumentCaptor.getValue())
                .matches(e -> e.getId() == employeeUpdateRequest.getId())
                .matches(e -> e.getRating() == employeeUpdateRequest.getRating());
        verify(employeeRepository).findById(employeeUpdateRequest.getId());
        verify(employeeRepository).save(any());
        verify(employeeMapper).employeeToEmployeeResponse(employee);
    }

    @Test
    void testAddNewTask_whenEmployeeAndTaskExist() {
        TaskEmployeeRequest taskEmployeeRequest = new TaskEmployeeRequest();
        taskEmployeeRequest.setTaskId(1L);
        taskEmployeeRequest.setEmployeeId(2L);
        Task task = new Task();
        task.setEmployees(new HashSet<>());
        Employee employee = new Employee();
        employee.setTasks(new HashSet<>());
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(taskService.findById(1L)).thenReturn(task);
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employeeArgumentCaptor.capture())).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(employeeResponse);

        employeeService.addNewTask(taskEmployeeRequest);

        assertThat(employeeArgumentCaptor.getValue())
                .matches(e -> e.getTasks().contains(task));
        verify(taskService).findById(1L);
        verify(employeeRepository).findById(2L);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).employeeToEmployeeResponse(employee);
    }

    @Test
    void testDelete_shouldReturnTrue_whenEmployeeExist() {
        Long id = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        employeeService.delete(id);

        verify(employeeRepository).findById(id);
        verify(employeeRepository).delete(employee);
    }
}