package ru.belonogov.task_service_spring.service;

import ru.belonogov.task_service_spring.domain.dto.request.CompanySaveRequest;
import ru.belonogov.task_service_spring.domain.dto.request.CompanyUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;

public interface CompanyService {

    CompanyResponse create(CompanySaveRequest companySaveRequest);

    Company findById(Long id);

    Company findByName(String companyName);

    CompanyResponse read(String name);

    CompanyResponse update(CompanyUpdateRequest companyUpdateRequest);

    void delete(Long id);

}
