package ru.belonogov.task_service_spring.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.belonogov.task_service_spring.util.YamlPropertySourceFactory;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class LiquibaseConfig {

    @Value("${spring.liquibase.change-log}")
    private String changelog;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(changelog);

        return springLiquibase;
    }
}
