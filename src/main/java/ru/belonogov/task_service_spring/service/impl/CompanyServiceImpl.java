package ru.belonogov.task_service_spring.service.impl;

import org.springframework.stereotype.Service;
import ru.belonogov.task_service_spring.domain.dto.mapper.CompanyMapper;
import ru.belonogov.task_service_spring.domain.dto.request.CompanySaveRequest;
import ru.belonogov.task_service_spring.domain.dto.request.CompanyUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;
import ru.belonogov.task_service_spring.domain.exception.CompanyNotFoundException;
import ru.belonogov.task_service_spring.domain.repository.CompanyRepository;
import ru.belonogov.task_service_spring.service.CompanyService;
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyResponse create(CompanySaveRequest companySaveRequest) {
        Company company = new Company();
        company.setName(companySaveRequest.getName());
        Company save = companyRepository.save(company);

        return companyMapper.companyToCompanyResponse(save);
    }

    @Override
    public Company findById(Long id) {

        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("company not found"));
    }

    @Override
    public Company findByName(String companyName) {

        return companyRepository.findCompanyByName(companyName).orElseThrow(() -> new CompanyNotFoundException("company not found"));
    }

    @Override
    public CompanyResponse read(String name) {
        Company byName = findByName(name);
        CompanyResponse companyResponse = companyMapper.companyToCompanyResponse(byName);
        return companyResponse;
    }

    @Override
    public CompanyResponse update(CompanyUpdateRequest companyRequest) {
        Long id = companyRequest.getId();
        Company company = findById(id);
        company.setName(companyRequest.getName());
        Company save = companyRepository.save(company);

        return companyMapper.companyToCompanyResponse(save);
    }

    @Override
    public void delete(Long id) {
        Company company = findById(id);
        companyRepository.delete(company);
    }
}
