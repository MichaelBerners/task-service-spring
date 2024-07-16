package ru.belonogov.task_service_spring.domain.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
    @SequenceGenerator(name = "employees_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private int rating;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties(value = "employees")
    private Company company;
    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Task> tasks;

    public void addTask(Task task) {
        this.tasks.add(task);
        task.getEmployees().add(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.getEmployees().remove(this);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRating() {
        return rating;
    }

    public Company getCompany() {
        return company;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
