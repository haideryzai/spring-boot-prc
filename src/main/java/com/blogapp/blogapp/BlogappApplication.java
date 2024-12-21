package com.blogapp.blogapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import javax.sql.DataSource;
import java.sql.Connection;




@SpringBootApplication
public class BlogappApplication {

    private static final Logger logger = LoggerFactory.getLogger(BlogappApplication.class);

    private final DataSource dataSource;

    public BlogappApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Successfully connected to the database: {}", connection.getMetaData().getURL());
        } catch (Exception e) {
            logger.error("Failed to connect to the database", e);
        }
    }
}