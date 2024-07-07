package ru.belonogov.task_service_spring.domain.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse taskToTaskResponse(Task task);

}
