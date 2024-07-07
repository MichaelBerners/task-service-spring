package ru.belonogov.task_service_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.belonogov.task_service_spring")
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource(PostgresTestContainer postgresTestContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresTestContainer.getDriverClassName());
        dataSource.setUrl(postgresTestContainer.getJdbcUrl());
        dataSource.setUsername(postgresTestContainer.getUsername());
        dataSource.setPassword(postgresTestContainer.getPassword());

        return dataSource;
    }
}
