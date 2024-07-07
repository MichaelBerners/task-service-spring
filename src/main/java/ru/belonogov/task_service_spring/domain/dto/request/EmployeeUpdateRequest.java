package ru.belonogov.task_service_spring.domain.dto.request;

public class EmployeeUpdateRequest {

    private Long id;
    private int rating;

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
