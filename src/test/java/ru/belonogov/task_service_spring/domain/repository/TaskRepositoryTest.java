package ru.belonogov.task_service_spring.domain.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.entity.Task;
import ru.belonogov.task_service_spring.domain.entity.TaskStatus;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSave_shouldSaveAndReturnTask() {
        Task task = new Task();
        task.setName("Название нового задания");
        task.setDescription("описание нового задания");
        task.setRating(5);
        task.setTaskStatus(TaskStatus.SEARCH_FOR_EMPLOYEES);

        Task result = taskRepository.save(task);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "Название нового задания")
                .hasFieldOrPropertyWithValue("description", "описание нового задания");
    }


    @Test
    void testFindById_shouldReturnOptionalOfTask_whenTaskExist() {
        assertThat(taskRepository.findById(3L)).isPresent();
    }

    @Test
    void testFindById_shouldReturnOptionalOfEmpty_whenTaskIsNotExist() {
        assertThat(taskRepository.findById(300L)).isEmpty();
    }

    @Test
    void testFindByName_shouldReturnOptionalOfTask_whenTaskExist() {
        assertThat(taskRepository.findTaskByName("Обустройство территории")).isPresent();
    }

    @Test
    void testFindByName_shouldReturnOptionalOfEmpty_whenTaskIsNotExist() {
        assertThat(taskRepository.findTaskByName("other task")).isEmpty();
    }

    @Test
    void testFindAllByEmployee_shouldReturnListTask_whenEmployeeExist() {
        assertThat(taskRepository.findAllByEmployee(1L)).isNotEmpty();
    }

    @Test
    void testFindAllByEmployee_shouldReturnEmptyList_whenEmployeeIsNotExist() {
        assertThat(taskRepository.findAllByEmployee(100L)).isEmpty();
    }

    @Test
    void testDelete_shouldTaskDelete_whenTaskExist() {
        Task task= new Task();
        task.setId(5L);
        taskRepository.delete(task);
        assertThat(taskRepository.count()).isEqualTo(5);
    }
}