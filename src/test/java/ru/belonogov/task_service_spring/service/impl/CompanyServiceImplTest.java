package ru.belonogov.task_service_spring.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.belonogov.task_service_spring.domain.dto.mapper.CompanyMapper;
import ru.belonogov.task_service_spring.domain.dto.request.CompanySaveRequest;
import ru.belonogov.task_service_spring.domain.dto.request.CompanyUpdateRequest;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;
import ru.belonogov.task_service_spring.domain.exception.CompanyNotFoundException;
import ru.belonogov.task_service_spring.domain.repository.CompanyRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @InjectMocks
    private CompanyServiceImpl companyService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyMapper companyMapper;
    @Captor
    private ArgumentCaptor<Company> companyArgumentCaptor;

    @Test
    void testCreate_shouldReturnCompanyDTO_whenCompanySaveInDB() {
        CompanySaveRequest companySaveRequest = new CompanySaveRequest();
        companySaveRequest.setName("Gazprom");
        Company company = new Company();
        company.setName(companySaveRequest.getName());
        CompanyResponse companyResponse = new CompanyResponse();
        when(companyRepository.save(argThat(arg -> arg.getName().equals(companySaveRequest.getName())))).thenReturn(company);
        when(companyMapper.companyToCompanyResponse(company)).thenReturn(companyResponse);

        CompanyResponse result = companyService.create(companySaveRequest);

        assertThat(result).isEqualTo(companyResponse);
        verify(companyRepository).save(any());
        verify(companyMapper).companyToCompanyResponse(any());
    }

    @Test
    void testFindById_shouldReturnCompanyDTO_whenCompanyExist() {
        Long id = 3L;
        Company company = new Company();
        company.setId(id);
        CompanyResponse companyResponse = new CompanyResponse();
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        Company result = companyService.findById(id);

        assertThat(result).isEqualTo(company);
        verify(companyRepository).findById(id);
    }

    @Test
    void testFindById_shouldReturnCompanyNotFoundException_whenCompanyIsNotExist() {
        Long id = 3L;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(id));
        verify(companyRepository).findById(3L);
    }

    @Test
    void testFindByName_shouldReturnCompanyDTO_whenCompanyExist() {
        String companyName = "Luloil";
        Company company = new Company();
        company.setName(companyName);
        CompanyResponse companyResponse = new CompanyResponse();
        when(companyRepository.findCompanyByName(companyName)).thenReturn(Optional.of(company));

        Company result = companyService.findByName(companyName);

        assertThat(result).isEqualTo(company);
        verify(companyRepository).findCompanyByName(companyName);
    }

    @Test
    void testFindByName_shouldReturnCompanyNotFoundException_whenCompanyIsNotExist() {
        String companyName = "Luloil";
        Company company = new Company();
        company.setName(companyName);
        when(companyRepository.findCompanyByName(companyName)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class,  () -> companyService.findByName(companyName));
    }

    @Test
    void testUpdate_shouldReturnCompanyResponse_whenCompanyUpdate() {
        CompanyUpdateRequest companyUpdateRequest = new CompanyUpdateRequest();
        companyUpdateRequest.setId(1L);
        companyUpdateRequest.setName("TatNeft");
        Company company = new Company();
        company.setId(companyUpdateRequest.getId());
        company.setName(companyUpdateRequest.getName());
        CompanyResponse companyResponse = new CompanyResponse();
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(companyArgumentCaptor.capture())).thenReturn(company);
        when(companyMapper.companyToCompanyResponse(company)).thenReturn(companyResponse);

        CompanyResponse result = companyService.update(companyUpdateRequest);

        assertThat(result).isEqualTo(companyResponse);
        assertThat(companyArgumentCaptor.getValue())
                .matches(e -> e.getId().equals(companyUpdateRequest.getId()))
                .matches(e -> e.getName().equals(companyUpdateRequest.getName()));
        verify(companyRepository).save(company);
        verify(companyMapper).companyToCompanyResponse(company);
    }


    @Test
    void testDelete_shouldReturnTrue_whereCompanyExist() {
        Long id = 1L;
        Company company = new Company();
        company.setId(id);
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        companyService.delete(id);

        verify(companyRepository).findById(id);
        verify(companyRepository).delete(company);
    }

}