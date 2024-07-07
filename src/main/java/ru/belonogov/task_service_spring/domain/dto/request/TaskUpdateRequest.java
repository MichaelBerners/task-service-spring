package ru.belonogov.task_service_spring.domain.dto.request;

import ru.belonogov.task_service_spring.domain.entity.TaskStatus;

public class TaskUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private TaskStatus taskStatus;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
