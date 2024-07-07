package ru.belonogov.task_service_spring.domain.dto.response;

import java.util.Set;

public class TaskResponse {

    private String name;
    private String description;
    private int rating;
    private String taskStatus;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

}
