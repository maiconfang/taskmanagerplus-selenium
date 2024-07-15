package com.taskmanagerplus.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class for managing the JdbcTemplate instance in the Task Manager Plus application.
 * 
 * <p>This class provides methods to initialize and access the JdbcTemplate for database operations,
 * as well as utility methods for inserting and cleaning up test data.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-15
 * Version: 1.0
 */

public class JdbcTemplateSingleton {

    private static JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateSingleton.class);

    private JdbcTemplateSingleton() {
        // private constructor to prevent instantiation
    }

    public static JdbcTemplate getInstance() {
        if (jdbcTemplate == null) {
            synchronized (JdbcTemplateSingleton.class) {
                if (jdbcTemplate == null) {
                    DriverManagerDataSource dataSource = new DriverManagerDataSource();
                    Properties properties = loadProperties();
                    dataSource.setUrl(properties.getProperty("spring.datasource.url"));
                    dataSource.setUsername(properties.getProperty("spring.datasource.username"));
                    dataSource.setPassword(properties.getProperty("spring.datasource.password"));
                    dataSource.setDriverClassName(properties.getProperty("spring.datasource.driver-class-name"));
                    jdbcTemplate = new JdbcTemplate(dataSource);
                }
            }
        }
        return jdbcTemplate;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = JdbcTemplateSingleton.class.getClassLoader().getResourceAsStream("application-test.properties")) {
            if (input == null) {
                logger.error("Sorry, unable to find application-test.properties");
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
        }
        return properties;
    }

    public static void insertTaskData(String title, String description, String dueDate, boolean completed) {
        try {
            String sql = "INSERT INTO task (title, description, due_date, completed) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, title, description, dueDate, completed ? 1 : 0);
            logger.info("Inserted task data: title={}, description={}, dueDate={}, completed={}", title, description, dueDate, completed);
        } catch (Exception e) {
            logger.error("Error inserting task data", e);
        }
    }

    public static void cleanupTestDataTask(String titlePattern) {
        try {
            String sql = "DELETE FROM task WHERE title LIKE ?";
            jdbcTemplate.update(sql, titlePattern + '%');
            logger.info("Cleaned up task data with title pattern: {}", titlePattern);
        } catch (Exception e) {
            logger.error("Error cleaning up task data", e);
        }
    }

    // Additional methods for inserting and cleaning data in other tables can be added here as needed
}
