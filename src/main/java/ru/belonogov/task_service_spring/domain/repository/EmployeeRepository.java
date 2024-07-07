package ru.belonogov.task_service_spring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.belonogov.task_service_spring.domain.entity.Employee;
import ru.belonogov.task_service_spring.domain.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e join fetch e.company c where e.id = :employeeId")
    Optional<Employee> findById(@Param("employeeId") Long employeeId);

    @Query("select e from Employee e join fetch e.tasks t join fetch e.company c where t.id = :taskId")
    List<Employee> findAllByTask(@Param("taskId") Long taskId);

    @Query("select e from Employee e join fetch e.company c where c.name = :companyName")
    List<Employee> findByCompanyName(@Param("companyName") String companyName);
}
