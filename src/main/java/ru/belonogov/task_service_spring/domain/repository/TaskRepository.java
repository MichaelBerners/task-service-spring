package ru.belonogov.task_service_spring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t join fetch t.employees e where e.id = :employeeId")
    List<Task> findAllByEmployee(@Param("employeeId") Long employeeId);

    @Query("select t from Task t left join fetch t.employees where t.id = :id")
    Optional<Task> findById(@Param("id") Long id);

    Optional<Task> findTaskByName(String name);

}
