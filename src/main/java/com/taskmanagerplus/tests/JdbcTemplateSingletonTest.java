package com.taskmanagerplus.tests;

import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.taskmanagerplus.config.JdbcTemplateSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for verifying the functionality of the JdbcTemplateSingleton in the Task Manager Plus application.
 * 
 * <p>This class provides tests to verify the initialization, data insertion, and data cleanup functionality
 * of the JdbcTemplateSingleton.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-17
 * Version: 1.0
 */
public class JdbcTemplateSingletonTest {

    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateSingletonTest.class);

    @BeforeClass
    public void setUpClass() {
        // Get the singleton instance of JdbcTemplate
        jdbcTemplate = JdbcTemplateSingleton.getInstance();
    }
    
    @AfterMethod
    public void tearDown() {
    	JdbcTemplateSingleton.cleanupTestDataTask("Test Task");
        logger.info("Test data cleaned up");
    }

    /**
     * Test to check if the JdbcTemplate is initialized properly.
     * 
     * <p>Scenario: Verify that the JdbcTemplate instance is properly initialized.</p>
     * <p>Expected Result: The JdbcTemplate instance should not be null.</p>
     */
    @Test
    public void testJdbcTemplateInitialization() {
        Assert.assertNotNull(jdbcTemplate, "JdbcTemplate should be initialized");
        logger.info("JdbcTemplate initialization test passed");
    }

    /**
     * Test to verify the insertion and cleanup of task data.
     * 
     * <p>Scenario: Insert a test task into the database and verify its insertion.
     * Then, clean up the test task from the database and verify its removal.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Insert a test task with title 'Test Task Insert'.</li>
     * <li>Query the database to ensure the task was inserted.</li>
     * <li>Clean up the test task from the database.</li>
     * <li>Query the database to ensure the task was removed.</li>
     * </ol>
     * <p>Expected Result: The task should be inserted and then removed from the database.</p>
     */
    @Test
    public void testInsertAndCleanupTaskData() {
        // Insert test data
        JdbcTemplateSingleton.insertTaskData("Test Task Insert", "Test Description Insert", "2024-07-17", false);

        // Verify the data insertion
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE title = 'Test Task Insert'", Integer.class);
        Assert.assertEquals(count, 1, "One test task should be inserted.");
        logger.info("Test data insertion verified");

        // Cleanup test data
        JdbcTemplateSingleton.cleanupTestDataTask("Test Task Insert");

        // Verify the data cleanup
        count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM task WHERE title = 'Test Task Insert'", Integer.class);
        Assert.assertEquals(count, 0, "Test task should be cleaned up.");
        logger.info("Test data cleanup verified");
    }
}
