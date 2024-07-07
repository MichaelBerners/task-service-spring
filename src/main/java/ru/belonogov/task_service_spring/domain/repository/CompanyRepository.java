package ru.belonogov.task_service_spring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.belonogov.task_service_spring.domain.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByName(String companyName);
}
