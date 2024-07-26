package com.taskmanagerplus.tests;

import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import com.taskmanagerplus.pages.DeleteConfirmationPage;
import com.taskmanagerplus.pages.TaskSearchPage;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.utils.ExcelUtils;

import io.qameta.allure.Attachment;

/**
 * Test class for the task search functionality in the Task Manager Plus application.
 * 
 * <p>This class provides tests to verify the functionality of the task search page,
 * including entering search criteria, performing searches, and interacting with
 * the search results.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */

public class TaskSearchTest extends BaseTest {

    private static ExcelUtils excelUtils;
    
    private TaskSearchPage taskSearchPage;
    
    private static final Logger logger = LoggerFactory.getLogger(TaskSearchTest.class);

    @BeforeAll
    public static void setUpClass() {
        // Initialize ExcelUtils with the path to the LoginCredentials.xlsx file
        excelUtils = new ExcelUtils("testdata/LoginCredentials.xlsx");
        
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        
        // Read credentials from Excel file
        String username = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Username");
        String password = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Password");

        // Perform login before navigating to the Task Search Page
        performLogin(username, password);
        taskSearchPage = navigateToTaskPage();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Task Search Page");
        logger.info("Performed login and navigated to Task Search Page");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            saveScreenshotPNG(driver);
            savePageSource(driver);
        }
        cleanupTestData();
        logger.info("Test data cleaned up and browser closed");
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html")
    public String savePageSource(WebDriver driver) {
        return driver.getPageSource();
    }
    
    private void cleanupTestData() {
        JdbcTemplateSingleton.cleanupTestDataTask("Test Task");
        if (driver != null) {
            driver.quit();
        }
    }

    private void insertTestData() {
        JdbcTemplateSingleton.insertTaskData("Test Task A", "Test Description A", "2024-07-15", false);
        JdbcTemplateSingleton.insertTaskData("Test Task B", "Test Description B", "2024-07-16", true);
        JdbcTemplateSingleton.insertTaskData("Test Task C", "Test Description C", "2023-12-31", true);
        JdbcTemplateSingleton.insertTaskData("Test Task D", "Test Description D", "2024-01-01", false);
        JdbcTemplateSingleton.insertTaskData("Test Task E", "Test Description E", "2024-01-15", true);
        JdbcTemplateSingleton.insertTaskData("Test Task F", "Test Description F", "2024-02-01", false);
        JdbcTemplateSingleton.insertTaskData("Test Task G", "Test Description G", "2024-02-15", true);
        JdbcTemplateSingleton.insertTaskData("Test Task H", "Test Description H", "2024-03-01", false);
        JdbcTemplateSingleton.insertTaskData("Test Task I", "Test Description I", "2024-03-15", true);
        JdbcTemplateSingleton.insertTaskData("Test Task J", "Test Description J", "2024-04-01", false);
        JdbcTemplateSingleton.insertTaskData("Test Task K", "Test Description K", "2024-04-15", true);
        JdbcTemplateSingleton.insertTaskData("Test Task L", "Test Description L", "2024-05-01", false);
        JdbcTemplateSingleton.insertTaskData("Test Task M", "Test Description M", "2024-05-15", true);
    }


    @Test
    public void searchTask_withValidTitleAndDescription_shouldReturnCorrectTask() {
        insertTestData();
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: searchTask_withValidTitleAndDescription_shouldReturnCorrectTask");
        logger.info("Starting test: searchTask_withValidTitleAndDescription_shouldReturnCorrectTask");
        
        taskSearchPage.enterTitle("Test Task A");
        taskSearchPage.enterDescription("Test Description A");
        taskSearchPage.clickSearchButton();

        // Add assertions to verify the search results
        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task A", wait);
        Assertions.assertNotNull(taskRow, "Test Task A should be present in the search results.");

        ExtentReportManager.getTest().log(Status.PASS, "Task search test passed");
        logger.info("Task search test passed");
    }



    /**
     * Test for the Title Field:
     * 
     * <p>Scenario: Search tasks by title.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Insert test data.</li>
     * <li>Enter a title in the title search field.</li>
     * <li>Click the "Consult Records" button.</li>
     * <li>Validate that only tasks matching the entered title are displayed.</li>
     * </ol>
     * <p>Expected Result: Only tasks that match the entered title should be displayed.
     * All columns (Title, Description, Due Date, Completed, Actions) should be validated.</p>
     */
    @Test
    public void searchTask_byTitle_shouldReturnMatchingTasks() {
        insertTestData();
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: searchTask_byTitle_shouldReturnMatchingTasks");
        logger.info("Starting test: searchTask_byTitle_shouldReturnMatchingTasks");
        
        taskSearchPage.enterTitle("Test Task A");
        taskSearchPage.clickSearchButton();

        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task A", wait);
        Assertions.assertNotNull(taskRow, "Test Task A should be present in the search results.");
        
        // Validate Description
        String description = taskSearchPage.getTaskDescription(taskRow);
        Assertions.assertEquals(description, "Test Description A", "The description should match 'Test Description A'.");

        // Validate Due Date
        String dueDate = taskSearchPage.getTaskDueDate(taskRow);
        Assertions.assertEquals(dueDate, "2024-07-15", "The due date should match '2024-07-15'.");

        // Validate Completed Status
        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assertions.assertEquals(completed, "No", "The task should not be completed.");

        // Validate Actions
        Assertions.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assertions.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task search by title test passed");
        logger.info("Task search by title test passed");
    }

    
    /**
     * Test for the Description Field:
     * 
     * Scenario: Search tasks by description.
     * Steps: Enter a description in the description search field and click the "Consult Records" button.
     * Expected Result: Only tasks that match the entered description should be displayed.
     * All columns (Title, Description, Due Date, Completed, Actions) should be validated.
     */
    @Test
    public void searchTask_byDescription_shouldReturnMatchingTasks() {
        insertTestData();
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: searchTask_byDescription_shouldReturnMatchingTasks");
        logger.info("Starting test: searchTask_byDescription_shouldReturnMatchingTasks");
        
        taskSearchPage.enterDescription("Test Description A");
        taskSearchPage.clickSearchButton();

        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task A", wait);
        Assertions.assertNotNull(taskRow, "Test Task A should be present in the search results.");
        
        // Validate Title
        String title = taskSearchPage.getTaskTitle(taskRow);
        Assertions.assertEquals(title, "Test Task A", "The title should match 'Test Task A'.");

        // Validate Due Date
        String dueDate = taskSearchPage.getTaskDueDate(taskRow);
        Assertions.assertEquals(dueDate, "2024-07-15", "The due date should match '2024-07-15'.");

        // Validate Completed Status
        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assertions.assertEquals(completed, "No", "The task should not be completed.");

        // Validate Actions
        Assertions.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assertions.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task search by description test passed");
        logger.info("Task search by description test passed");
    }

    
    /**
     * Test for the Due Date Field:
     * 
     * Scenario: Search tasks by due date.
     * Steps: Enter a due date in the due date field and click the "Consult Records" button.
     * Expected Result: Only tasks that match the entered due date should be displayed.
     * All columns (Title, Description, Due Date, Completed, Actions) should be validated.
     */
    @Test
    public void searchTask_byDueDate_shouldReturnMatchingTasks() {
        insertTestData();
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: searchTask_byDueDate_shouldReturnMatchingTasks");
        logger.info("Starting test: searchTask_byDueDate_shouldReturnMatchingTasks");
        
        taskSearchPage.enterDueDate("2024-07-15");
        
        // Click on another element to trigger the form validation
        taskSearchPage.clickTitleInput();
        
        taskSearchPage.clickSearchButton();

        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task A", wait);
        Assertions.assertNotNull(taskRow, "Test Task A should be present in the search results.");
        
        // Validate Title
        String title = taskSearchPage.getTaskTitle(taskRow);
        Assertions.assertEquals(title, "Test Task A", "The title should match 'Test Task A'.");

        // Validate Description
        String description = taskSearchPage.getTaskDescription(taskRow);
        Assertions.assertEquals(description, "Test Description A", "The description should match 'Test Description A'.");

        // Validate Completed Status
        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assertions.assertEquals(completed, "No", "The task should not be completed.");

        // Validate Actions
        Assertions.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assertions.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task search by due date test passed");
        logger.info("Task search by due date test passed");
    }
    
    /**
     * Test for the Completion Status Checkboxes:
     * 
     * Scenario: Filter tasks by completion status.
     * Steps: Check the "Completed" checkbox and click the "Consult Records" button.
     * Expected Result: Only tasks that are completed should be displayed.
     * All columns (Title, Description, Due Date, Completed, Actions) should be validated.
     */
    @Test
    public void filterTask_byCompletedStatus_shouldReturnCompletedTasks() {
        insertTestData();
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: filterTask_byCompletedStatus_shouldReturnCompletedTasks");
        logger.info("Starting test: filterTask_byCompletedStatus_shouldReturnCompletedTasks");
        
        taskSearchPage.selectCompletedCheckbox();
        taskSearchPage.clickSearchButton();

        List<WebElement> completedTasks = taskSearchPage.getCompletedTasks();
        for (WebElement task : completedTasks) {
            // Validate Title
            String title = taskSearchPage.getTaskTitle(task);
            Assertions.assertTrue(title.startsWith("Test Task"), "The title should start with 'Test Task'.");

            // Validate Description
            String description = taskSearchPage.getTaskDescription(task);
            Assertions.assertTrue(description.startsWith("Test Description"), "The description should start with 'Test Description'.");

            // Validate Due Date
            String dueDate = taskSearchPage.getTaskDueDate(task);
            Assertions.assertNotNull(dueDate, "The due date should not be null.");

            // Validate Completed Status
            String completed = taskSearchPage.getTaskCompletedStatus(task);
            Assertions.assertEquals(completed, "Yes", "The task should be completed.");

            // Validate Actions
            Assertions.assertTrue(taskSearchPage.hasEditButton(task), "The edit button should be present.");
            Assertions.assertTrue(taskSearchPage.hasDeleteButton(task), "The delete button should be present.");
        }

        ExtentReportManager.getTest().log(Status.PASS, "Task filter by completed status test passed");
        logger.info("Task filter by completed status test passed");
    }
    
    /**
     * Test for the Create Record Button:
     * 
     * Scenario: Verify navigation to the task creation page.
     * Steps: Click the "Create Record" button.
     * Expected Result: The system should navigate to the task creation page.
     * Validate that the URL contains "/task/new".
     */
    @Test
    public void createTaskButton_shouldNavigateToCreateTaskPage() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: createTaskButton_shouldNavigateToCreateTaskPage");
        logger.info("Starting test: createTaskButton_shouldNavigateToCreateTaskPage");
        
        taskSearchPage.clickCreateTaskButton();
        
        Assertions.assertTrue(driver.getCurrentUrl().contains("/task/new"), "Should navigate to the create task page.");

        ExtentReportManager.getTest().log(Status.PASS, "Create new task navigation test passed");
        logger.info("Create new task navigation test passed");
    }
    

    /**
     * Test to fill the fields title, description, and completed status
     * and verify if the data is inserted correctly.
     */
    @Test
    public void filterTask_byTitleDescriptionAndCompletedStatus_shouldReturnCorrectTasks() {
        // Insert test data
        JdbcTemplateSingleton.insertTaskData("Test Task C", "Test Description C", "2023-12-31", true);
        
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: filterTask_byTitleDescriptionAndCompletedStatus_shouldReturnCorrectTasks");
        logger.info("Starting test: filterTask_byTitleDescriptionAndCompletedStatus_shouldReturnCorrectTasks");

        // Fill in the search fields
        taskSearchPage.enterTitle("Test Task C");
        taskSearchPage.enterDescription("Test Description C");
        taskSearchPage.selectCompletedCheckbox();
        taskSearchPage.clickSearchButton();

        // Verify the search results
        WebElement taskRow = taskSearchPage.waitForTaskRow("Test Task C", wait);
        Assertions.assertNotNull(taskRow, "Test Task C should be present in the search results.");
        
        // Validate Description
        String description = taskSearchPage.getTaskDescription(taskRow);
        Assertions.assertEquals(description, "Test Description C", "The description should match 'Test Description C'.");

        // Validate Due Date
        String dueDate = taskSearchPage.getTaskDueDate(taskRow);
        Assertions.assertEquals(dueDate, "2023-12-31", "The due date should match '2023-12-31'.");

        // Validate Completed Status
        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assertions.assertEquals(completed, "Yes", "The task should be completed.");

        // Validate Actions
        Assertions.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assertions.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task filter by title, description, and completed status test passed");
        logger.info("Task filter by title, description, and completed status test passed");
    }
    
    /**
     * Test to validate that appropriate error messages are displayed when no results are found for a specific query.
     */
    @Test
    public void searchTask_withNonExistentCriteria_shouldDisplayErrorMessage() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: searchTask_withNonExistentCriteria_shouldDisplayErrorMessage");
        logger.info("Starting test: searchTask_withNonExistentCriteria_shouldDisplayErrorMessage");

        // Fill in the search fields with non-existent criteria
        taskSearchPage.enterTitle("NonExistent Task");
        taskSearchPage.enterDescription("NonExistent Description");
        taskSearchPage.clickSearchButton();

        // Verify that the error message is displayed
        WebElement errorMessage = taskSearchPage.getErrorMessage();
        Assertions.assertNotNull(errorMessage, "Error message should be displayed when no results are found.");
        Assertions.assertEquals(errorMessage.getText(), "No records found", "The error message should indicate that no results were found.");

        ExtentReportManager.getTest().log(Status.PASS, "Error message validation test passed");
        logger.info("Error message validation test passed");
    }


    /**
     * Test to validate pagination functionality.
     * This test ensures that navigating between different pages of results works correctly.
     */
    @Test
    public void pagination_shouldWorkCorrectly() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: pagination_shouldWorkCorrectly");
        logger.info("Starting test: pagination_shouldWorkCorrectly");

        // Insert test data
        insertTestData();
        
        // Fill in the search fields with non-existent criteria
        taskSearchPage.enterTitle("Test Task");
        taskSearchPage.clickSearchButton();

        // Verify the first task on the second page
        WebElement firstTaskOnSecondPage = taskSearchPage.getFirstTaskOnCurrentPage();
        Assertions.assertNotNull(firstTaskOnSecondPage, "There should be a task displayed on the second page.");
        Assertions.assertTrue(firstTaskOnSecondPage.getText().contains("Test Task A"), "The first task on the first page should be a task from the inserted test data.");

        // Navigate to the second page
        taskSearchPage.clickPaginationNext();
        
        // Verify the first task on the first page
        WebElement firstTaskOnFirstPage = taskSearchPage.getFirstTaskOnCurrentPage();
        Assertions.assertNotNull(firstTaskOnFirstPage, "There should be a task displayed on the first page.");
        Assertions.assertTrue(firstTaskOnFirstPage.getText().contains("Test Task K"), "The first task on the second page should be a task from the inserted test data.");

        ExtentReportManager.getTest().log(Status.PASS, "Pagination functionality test passed");
        logger.info("Pagination functionality test passed");
    }
    
    /**
     * Test to validate the system's resistance to SQL injection and script injection attacks.
     * This test attempts to input malicious data into the search fields and ensures the system handles it securely.
     */
    @Test
    public void securityTest_preventSQLInjectionAndScriptInjection() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting security test: preventSQLInjectionAndScriptInjection");
        logger.info("Starting security test: preventSQLInjectionAndScriptInjection");

        // Attempt SQL injection in the title field
        taskSearchPage.enterTitle("Test Task'; DROP TABLE task;--");
        taskSearchPage.clickSearchButton();

        // Verify no malicious actions are executed and proper error handling occurs
        WebElement errorMessage = taskSearchPage.getErrorMessage();
        Assertions.assertNotNull(errorMessage, "Error message should be displayed when SQL injection attempt is made.");
        Assertions.assertEquals(errorMessage.getText(), "No records found", "The error message should indicate that no results were found.");

        // Attempt script injection in the description field
        taskSearchPage.enterDescription("<script>alert('XSS');</script>");
        taskSearchPage.clickSearchButton();

        // Verify the script is not executed and proper error handling occurs
        errorMessage = taskSearchPage.getErrorMessage();
        Assertions.assertNotNull(errorMessage, "Error message should be displayed when script injection attempt is made.");
        Assertions.assertEquals(errorMessage.getText(), "No records found", "The error message should indicate that no results were found.");

        ExtentReportManager.getTest().log(Status.PASS, "Security test for SQL and script injection passed");
        logger.info("Security test for SQL and script injection passed");
    }


    /**
     * Test to validate the deletion of a task from the task list.
     * This test ensures that a task can be successfully removed from the list,
     * and that the task is no longer present in the search results after deletion.
     */
    @Test
    public void deleteTask_shouldRemoveTaskFromList() {
        // Insert test data
        insertTestData();

        ExtentReportManager.getTest().log(Status.INFO, "Starting test: deleteTask_shouldRemoveTaskFromList");
        logger.info("Starting test: deleteTask_shouldRemoveTaskFromList");

        // Search for the task to ensure it exists before deletion
        taskSearchPage.enterTitle("Test Task A");
        taskSearchPage.clickSearchButton();

        // Get the delete button for the specific task and click it
        WebElement deleteButton = taskSearchPage.getDeleteButtonForTask("Test Task A");
        deleteButton.click();

        // Handle the delete confirmation dialog
        DeleteConfirmationPage deleteConfirmationPage = new DeleteConfirmationPage(driver);
        deleteConfirmationPage.clickYesButton();

        // Verify the success message
        WebElement successMessage = taskSearchPage.getConfirmRemoveMessage();
        Assertions.assertNotNull(successMessage, "The success message should be displayed after deletion.");
        Assertions.assertEquals(successMessage.getText().replace("×", "").trim(), "Successfully Removed", "The success message should indicate that the task was removed.");

        // Search again to ensure the task is no longer present
        taskSearchPage.enterTitle("Test Task A");
        taskSearchPage.clickSearchButton();
        
        // Verify that the No records found is displayed
        WebElement errorMessage = taskSearchPage.getErrorMessage();
        Assertions.assertEquals(errorMessage.getText(), "No records found", "Test Task A should not be present in the search results after deletion.");

        ExtentReportManager.getTest().log(Status.PASS, "Task deletion test passed");
        logger.info("Task deletion test passed");
    }



    
    



}
