package ru.belonogov.task_service_spring.domain.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.belonogov.task_service_spring.domain.dto.response.EmployeeResponse;
import ru.belonogov.task_service_spring.domain.entity.Employee;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "companyName", source = "company.name")
    EmployeeResponse employeeToEmployeeResponse(Employee employee);

}
