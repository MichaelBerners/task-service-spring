package ru.belonogov.task_service_spring.domain.dto.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.belonogov.task_service_spring.TestConfig;
import ru.belonogov.task_service_spring.domain.dto.response.CompanyResponse;
import ru.belonogov.task_service_spring.domain.entity.Company;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
class CompanyMapperTest {

    @Autowired
    private CompanyMapper companyMapper;

    @Test
    void companyToCompanyResponse() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Gazprom");

        CompanyResponse companyResponse = companyMapper.companyToCompanyResponse(company);

        assertThat(companyResponse)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .matches(e -> e.getName().equals(company.getName()));
    }
}