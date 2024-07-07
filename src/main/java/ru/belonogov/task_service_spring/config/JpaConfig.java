package ru.belonogov.task_service_spring.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.belonogov.task_service_spring.util.YamlPropertySourceFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@EnableJpaRepositories(basePackages = "ru.belonogov.task_service_spring.domain.repository")
@EnableTransactionManagement
public class JpaConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_NAME;
    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.userName}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;
    @Value("${spring.jpa.hibernate.dialect}")
    private String DIALECT;
    @Value("${spring.jpa.hibernate.show_sql}")
    private String SHOW_SQL;
    @Value("${spring.jpa.hibernate.hbm2ddl}")
    private String HBM2DDL;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(DRIVER_NAME);
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USERNAME);
        driverManagerDataSource.setPassword(PASSWORD);

        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("ru/belonogov/task_service_spring");
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Properties jpaProperties = new Properties();
        jpaProperties.put("DIALECT", DIALECT);
        jpaProperties.put("SHOW_SQL", SHOW_SQL);
        jpaProperties.put("HBM2DDL", HBM2DDL);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return jpaTransactionManager;
    }
}
