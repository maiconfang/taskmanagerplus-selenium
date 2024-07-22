package com.taskmanagerplus.tests;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.aventstack.extentreports.Status;
import com.taskmanagerplus.config.JdbcTemplateSingleton;
import com.taskmanagerplus.pages.NotificationPage;
import com.taskmanagerplus.pages.TaskRegisterPage;
import com.taskmanagerplus.pages.TaskSearchPage;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.utils.ExcelUtils;

/**
 * Test class for the task editing functionality in the Task Manager Plus application.
 * 
 * <p>This class provides tests to verify the functionality of editing a task,
 * including entering task details, saving the changes, and verifying the updates.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-17
 * Version: 1.0
 */
public class TaskEditTest extends BaseTest {

    private TaskRegisterPage taskRegisterPage;
    private TaskSearchPage taskSearchPage;
    private static ExcelUtils excelUtils;
    private static final Logger logger = LoggerFactory.getLogger(TaskEditTest.class);

    @BeforeAll
    public static void setUpClass() {
        // Initialize ExcelUtils with the path to the LoginCredentials.xlsx file
        excelUtils = new ExcelUtils("testdata/LoginCredentials.xlsx");
    }

    @BeforeEach
    public void setUp() {
        super.setUp();

        // Read credentials from the Excel file
        String username = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Username");
        String password = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Password");

        // Perform login before navigating to the Task Search Page
        performLogin(username, password);

        // Navigate to the Task Search Page after login
        taskSearchPage = navigateToTaskPage();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Task Search Page");
        logger.info("Performed login and navigated to Task Search Page");

        // Insert value of task 
        insertTestData();
        
        // Search for an existing task to edit
        taskSearchPage.enterTitle("Test Task A");
        taskSearchPage.clickSearchButton();
        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task A", wait);
        Assertions.assertNotNull(taskRow, "The task to edit should be present in the search results.");

        // Click the edit icon for the task
        taskSearchPage.clickEditButtonForTask("Test Task A");
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on 'Edit' icon for 'Test Task A'");
        logger.info("Clicked on 'Edit' icon for 'Test Task A'");

        // Initialize the TaskRegisterPage
        taskRegisterPage = new TaskRegisterPage(driver);
    }

    @AfterEach
    public void tearDown() {
        cleanupTestData();
        logger.info("Test data cleaned up and browser closed");
    }

    
    private void insertTestData() {
        JdbcTemplateSingleton.insertTaskData("Test Task A", "Test Description A", "2024-07-15", false);       
    }
    
    private void cleanupTestData() {
        JdbcTemplateSingleton.cleanupTestDataTask("Test Task");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test for Editing and Verifying a Task:
     * 
     * <p>Scenario: Edit an existing task and verify the changes.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Start the test and log the initial status.</li>
     * <li>Enter new title, description, and due date for the task.</li>
     * <li>Click on another element to trigger form validation.</li>
     * <li>Set the task as completed.</li>
     * <li>Click the save button.</li>
     * <li>Verify that a success message is displayed.</li>
     * <li>Log the success of the task update.</li>
     * <li>Navigate back to the task search page and search for the updated task by its title.</li>
     * <li>Validate that the updated task is present in the search results.</li>
     * <li>Verify that the task's description, due date, and completion status match the updated values.</li>
     * <li>Ensure that the edit and delete buttons are present for the task.</li>
     * <li>Log the success of the task verification.</li>
     * </ol>
     * <p>Expected Result: The task should be successfully updated with the new details and should be present in the search results with the correct information. The edit and delete buttons should be present.</p>
     */
    @Test
    public void editAndVerifyTask_shouldSucceed() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: editAndVerifyTask_shouldSucceed");

        String newTaskTitle = "Test Task A Edited";
        String newTaskDescription = "Test Description A Edited";
        String newTaskDueDate = "2024-07-20";

        taskRegisterPage.enterTitle(newTaskTitle);
        taskRegisterPage.enterDescription(newTaskDescription);
        taskRegisterPage.enterDueDate(newTaskDueDate);
        
        // Click on another element to trigger form validation
        taskRegisterPage.clickTitleInput();
        
        taskRegisterPage.setCompleted(true);

        // Click the save button
        taskRegisterPage.clickSaveButton();

        NotificationPage notificationPage = new NotificationPage(driver);
        WebElement successMessage = notificationPage.getConfirmRemoveMessage();
        Assertions.assertNotNull(successMessage, "The success message should be displayed after task update.");
        Assertions.assertEquals(successMessage.getText().replace("×", "").trim(), "Successfully Updated", "The success message should indicate that the task was updated.");

        ExtentReportManager.getTest().log(Status.PASS, "Task update test passed");
        logger.info("Task update test passed");

        // Navigate back to the task search page and verify the updated task
        taskSearchPage = navigateToTaskPage();
        taskSearchPage.enterTitle(newTaskTitle);
        taskSearchPage.clickSearchButton();

        WebElement taskRow = taskSearchPage.waitForTaskRow(newTaskTitle, wait);
        Assertions.assertNotNull(taskRow, "The updated task should be present in the search results.");

        String description = taskSearchPage.getTaskDescription(taskRow);
        Assertions.assertEquals(description, newTaskDescription, "The description should match.");

        String dueDate = taskSearchPage.getTaskDueDate(taskRow);
        Assertions.assertEquals(dueDate, newTaskDueDate, "The due date should match.");

        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assertions.assertEquals(completed, "Yes", "The task should be completed.");

        Assertions.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assertions.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task verification test passed");
        logger.info("Task verification test passed");
    }
}
