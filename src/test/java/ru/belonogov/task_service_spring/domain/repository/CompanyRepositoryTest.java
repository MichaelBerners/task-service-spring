package ru.belonogov.task_service_spring.domain.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.entity.Company;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void testSave_shouldSaveAndReturnCompany() {
        Company company = new Company();
        company.setName("Lukoil");

        Company result = companyRepository.save(company);

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "Lukoil");
    }

    @Test
    void testFindById_shouldReturnOptionalOfCompany_whenCompanyExists() {
        assertThat(companyRepository.findById(1L).isPresent()).isTrue();
    }

    @Test
    void testFindById_shouldReturnOptional_whenCompanyIsNotExists() {
        assertThat(companyRepository.findById(100L).isPresent()).isFalse();
    }

    @Test
    void testFindByName_shouldReturnOptionalOfCompany_whenCompanyExists() {
        String name = "Gazprom";
        assertThat(companyRepository.findCompanyByName(name).isPresent());
    }

    @Test
    void testFindByName_shouldReturnOptional_whenCompanyIsNotExists() {
        String name = "OtherCompany";
        assertThat(companyRepository.findCompanyByName(name).isPresent()).isFalse();
    }

    @Test
    void testDelete_shouldReturnTrue_whenCompanyDelete() {
        Company company = new Company();
        company.setId(4L);
        companyRepository.delete(company);

        assertThat(companyRepository.count()).isEqualTo(4);
    }


}