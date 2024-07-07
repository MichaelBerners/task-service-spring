package ru.belonogov.task_service_spring.domain.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.entity.Company;
import ru.belonogov.task_service_spring.domain.entity.Employee;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSave_shouldSaveAndReturnEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Alexei");
        employee.setLastName("Bubnov");
        employee.setRating(5);
        Company company = new Company();
        company.setId(1L);
        company.setName("Gazprom");
        company.setEmployees(Collections.emptySet());
        employee.setCompany(company);

        Employee result = employeeRepository.save(employee);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("firstName", "Alexei");
    }



    @Test
    void testFindById_shouldReturnOptionalOfEmployee_whenEmployeeExist() {
        assertThat(employeeRepository.findById(1L)).isPresent();
    }

    @Test
    void testFindById_shouldReturnOptionalOfEmpty_whenEmployeeIsNotExist() {
        assertThat(employeeRepository.findById(100L)).isEmpty();
    }

    @Test
    void testFindAllByCompany_shouldReturnListEmployeeIsNotEmpty_whenCompanyExist() {
        assertThat(employeeRepository.findByCompanyName("Gazprom")).isNotEmpty();
    }

    @Test
    void testFindAllByCompany_shouldReturnListEmployeeIsEmpty_whenCompanyIsNotExist() {
        assertThat(employeeRepository.findByCompanyName("OtherCompany")).isEmpty();
    }

    @Test
    void testFindAllByTask_shouldReturnListEmployee_whenTaskExist() {
        assertThat(employeeRepository.findAllByTask(3L)).isNotEmpty();
    }

    @Test
    void testFindAllByTask_shouldReturnEmptyList_whenTaskIsNotExist() {
        assertThat(employeeRepository.findAllByTask(100L)).isEmpty();
    }



    @Test
    void testDelete_shouldReturnTrue_whenEmployeeDelete() {
        Employee employee = new Employee();
        employee.setId(9L);
        employeeRepository.delete(employee);
        assertThat(employeeRepository.count()).isEqualTo(8);
    }

}