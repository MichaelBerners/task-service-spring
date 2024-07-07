package ru.belonogov.task_service_spring.domain.dto.mapper;


import org.mapstruct.Mapper;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;


@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyResponse companyToCompanyResponse(Company company);

}
