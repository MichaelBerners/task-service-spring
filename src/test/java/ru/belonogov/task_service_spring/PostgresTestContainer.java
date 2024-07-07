package ru.belonogov.task_service_spring;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
@Component
public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private final static String IMAGE_VERSION = "postgres:14";

    public PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    @PostConstruct
    public void init() {
        start();
    }

    @PreDestroy
    public void destroy() {
        stop();
    }
}
