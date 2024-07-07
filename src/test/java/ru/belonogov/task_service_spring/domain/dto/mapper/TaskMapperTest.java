package ru.belonogov.task_service_spring.domain.dto.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.dto.response.TaskResponse;
import ru.belonogov.task_service_spring.domain.entity.Task;
import ru.belonogov.task_service_spring.domain.entity.TaskStatus;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void taskToTaskResponse() {
        Task task = new Task();
        task.setName("task1");
        task.setDescription("description for task1");
        task.setRating(5);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);

        TaskResponse taskResponse = taskMapper.taskToTaskResponse(task);

        assertThat(taskResponse)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .matches(e -> e.getName().equals(task.getName()))
                .matches(e -> e.getDescription().equals(task.getDescription()))
                .matches(e -> e.getRating() == task.getRating())
                .matches(e -> e.getTaskStatus().equals(task.getTaskStatus().name()));
    }
}