package ru.belonogov.task_service_spring.domain.entity;



import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_seq")
    @SequenceGenerator(name = "tasks_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    private int rating;
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tasks_employee",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private Set<Employee> employees;

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getTasks().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
