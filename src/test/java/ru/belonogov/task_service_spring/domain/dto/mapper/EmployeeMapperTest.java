package ru.belonogov.task_service_spring.domain.dto.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;
import ru.belonogov.task_service_spring.domain.entity.Employee;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void employeeToEmployeeResponse() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Gazprom");
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setRating(7);
        employee.setCompany(company);
        employee.setTasks(Collections.emptySet());

        EmployeeResponse employeeResponse = employeeMapper.employeeToEmployeeResponse(employee);

        assertThat(employeeResponse)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .matches(e -> e.getFirstName().equals(employee.getFirstName()))
                .matches(e -> e.getLastName().equals(employee.getLastName()))
                .matches(e -> e.getRating() == employee.getRating())
                .matches(e -> e.getCompanyName().equals(employee.getCompany().getName()));


    }
}