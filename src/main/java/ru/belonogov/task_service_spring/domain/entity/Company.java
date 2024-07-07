package ru.belonogov.task_service_spring.domain.entity;


import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_seq")
    @SequenceGenerator(name = "company_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
