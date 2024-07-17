package com.taskmanagerplus.tests;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.taskmanagerplus.config.JdbcTemplateSingleton;
import com.taskmanagerplus.pages.NotificationPage;
import com.taskmanagerplus.pages.TaskRegisterPage;
import com.taskmanagerplus.pages.TaskSearchPage;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.utils.ExcelUtils;

/**
 * Test class for the task register functionality in the Task Manager Plus application.
 * 
 * <p>This class provides tests to verify the functionality of the task register page,
 * including entering task details, saving tasks, and verifying saved tasks.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-16
 * Version: 1.0
 */
public class TaskRegisterTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(TaskRegisterTest.class);

	private TaskRegisterPage taskRegisterPage;
    
    private TaskSearchPage taskSearchPage;
    
    private ExcelUtils excelUtils;
    

    @BeforeClass
    public void setUpClass() {
        // Initialize ExcelUtils with the path to the LoginCredentials.xlsx file
        excelUtils = new ExcelUtils("testdata/LoginCredentials.xlsx");

    }

    @BeforeMethod
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

        // Click on the "Create Record" button to navigate to the Task Register Page
        taskSearchPage.clickCreateTaskButton();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on 'Create Record' button to navigate to Task Register Page");
        logger.info("Clicked on 'Create Record' button to navigate to Task Register Page");

        // Initialize the TaskRegisterPage
        taskRegisterPage = new TaskRegisterPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        cleanupTestData();
        logger.info("Test data cleaned up and browser closed");
    }
    
    private void cleanupTestData() {
        JdbcTemplateSingleton.cleanupTestDataTask("Test Task");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test to verify that a task can be created with all fields filled.
     * 
     * <p>Scenario: Fill in all fields on the task registration form and save the task.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Enter a title in the title input field.</li>
     * <li>Enter a description in the description input field.</li>
     * <li>Enter a due date in the due date input field.</li>
     * <li>Check the completed checkbox.</li>
     * <li>Click the save button.</li>
     * </ol>
     * <p>Expected Result: The task should be successfully created and a success message should be displayed.</p>
     */
    @Test
    public void createTask_withAllFieldsFilled_shouldSucceed() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: createTask_withAllFieldsFilled_shouldSucceed");

        taskRegisterPage.enterTitle("Test Task title create withAllFieldsFilled");
        taskRegisterPage.enterDescription("Test Task description create withAllFieldsFilled");
        taskRegisterPage.enterDueDate("2024-07-16");
        taskRegisterPage.setCompleted(true);
        taskRegisterPage.clickSaveButton();
        
        // Verify the success message
        NotificationPage notificationPage = new NotificationPage(driver);
        WebElement successMessage = notificationPage.getConfirmRemoveMessage();
        Assert.assertNotNull(successMessage, "The success message should be displayed after deletion.");
        Assert.assertEquals(successMessage.getText().replace("×", "").trim(), "Successfully Created", "The success message should indicate that the task was created.");
        
        ExtentReportManager.getTest().log(Status.PASS, "Task creation with all fields filled test passed");
        logger.info("Task creation with all fields filled test passed");
    }
    
    /**
     * Test to verify that a task can be created with only mandatory fields filled.
     * 
     * <p>Scenario: Fill in only the mandatory fields on the task registration form and save the task.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Enter a title in the title input field.</li>
     * <li>Enter a description in the description input field.</li>
     * <li>Enter a due date in the due date input field.</li>
     * <li>Click another element to trigger form validation.</li>
     * <li>Click the save button.</li>
     * </ol>
     * <p>Expected Result: The task should be successfully created and a success message should be displayed.</p>
     */
    @Test
    public void createTask_withMandatoryFieldsOnly_shouldSucceed() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: createTask_withMandatoryFieldsOnly_shouldSucceed");

        taskRegisterPage.enterTitle("Test Task title create withMandatoryFieldsOnly");
        taskRegisterPage.enterDescription("Test Task description create withMandatoryFieldsOnly");
        taskRegisterPage.enterDueDate("2024-07-16");
        
        // Click on another element to trigger the form validation
        taskRegisterPage.clickTitleInput();
        
        // Click the save button
        taskRegisterPage.clickSaveButton();

        // Verify the success message
        NotificationPage notificationPage = new NotificationPage(driver);
        WebElement successMessage = notificationPage.getConfirmRemoveMessage();
        Assert.assertNotNull(successMessage, "The success message should be displayed after deletion.");
        Assert.assertEquals(successMessage.getText().replace("×", "").trim(), "Successfully Created", "The success message should indicate that the task was created.");

        ExtentReportManager.getTest().log(Status.PASS, "Task creation with mandatory fields only test passed");
    }
    
    /**
     * Test to verify that the back button navigates to the search page.
     * 
     * <p>Scenario: Click the back button on the task registration form to return to the task search page.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Click the back button.</li>
     * <li>Verify that the URL contains '/task/search'.</li>
     * </ol>
     * <p>Expected Result: The current URL should contain '/task/search', indicating successful navigation back to the search page.</p>
     */
    @Test
    public void backButton_shouldNavigateToSearchPage() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: backButton_shouldNavigateToSearchPage");

        taskRegisterPage.clickBackButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("/task"), "The current URL should contain '/task/search'.");
        ExtentReportManager.getTest().log(Status.PASS, "Back button navigation test passed");
    }
    
    
    /**
     * Test to verify the functionality of the completed checkbox.
     * 
     * <p>Scenario: Ensure the completed checkbox can be toggled correctly.</p>
     * <p>Steps:</p>
     * <ol>
     *   <li>Set the completed checkbox to true.</li>
     *   <li>Verify that the completed checkbox is checked.</li>
     *   <li>Set the completed checkbox to false.</li>
     *   <li>Verify that the completed checkbox is unchecked.</li>
     * </ol>
     * <p>Expected Result: The completed checkbox should be toggled correctly and the changes should be reflected accurately.</p>
     */
    @Test
    public void completedCheckbox_shouldToggleCorrectly() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: completedCheckbox_shouldToggleCorrectly");

        // Set the completed checkbox to true
        taskRegisterPage.setCompleted(true);
        // Verify that the completed checkbox is checked
        Assert.assertTrue(taskRegisterPage.isCompletedChecked(), "The completed checkbox should be checked.");

        // Set the completed checkbox to false
        taskRegisterPage.setCompleted(false);
        // Verify that the completed checkbox is unchecked
        Assert.assertFalse(taskRegisterPage.isCompletedChecked(), "The completed checkbox should be unchecked.");

        ExtentReportManager.getTest().log(Status.PASS, "Completed checkbox toggle test passed");
    }

	

    /**
     * Test to verify that a task can be created and subsequently verified in the task search page.
     * 
     * <p>Scenario: Create a new task by filling in all required fields on the task registration form,
     * save the task, and verify its presence and correctness in the task search results.</p>
     * <p>Steps:</p>
     * <ol>
     * <li>Enter a title in the title input field.</li>
     * <li>Enter a description in the description input field.</li>
     * <li>Enter a due date in the due date input field.</li>
     * <li>Set the completed status to false.</li>
     * <li>Click on another element to trigger form validation.</li>
     * <li>Click the save button to create the task.</li>
     * <li>Verify that a success message is displayed indicating the task was created.</li>
     * <li>Navigate back to the task search page.</li>
     * <li>Enter the title of the created task in the search field.</li>
     * <li>Click the search button to find the task.</li>
     * <li>Verify the task appears in the search results with the correct title, description, due date, and completed status.</li>
     * <li>Verify the presence of the edit and delete buttons for the task in the search results.</li>
     * </ol>
     * <p>Expected Result: The task should be successfully created, the success message should be displayed,
     * and the task should be correctly displayed in the search results with the expected details.</p>
     */
    @Test
    public void createAndVerifyTask_shouldSucceed() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: createAndVerifyTask_shouldSucceed");

        String taskTitle = "Test Task Verification";
        String taskDescription = "Test Task Description Verification";
        String taskDueDate = "2024-07-17";
        
        taskRegisterPage.enterTitle(taskTitle);
        taskRegisterPage.enterDescription(taskDescription);
        taskRegisterPage.enterDueDate(taskDueDate);
        taskRegisterPage.setCompleted(false);
        
        // Click on another element to trigger the form validation
        taskRegisterPage.clickTitleInput();
        
        // Click the save button
        taskRegisterPage.clickSaveButton();
        
        NotificationPage notificationPage = new NotificationPage(driver);
        WebElement successMessage = notificationPage.getConfirmRemoveMessage();
        Assert.assertNotNull(successMessage, "The success message should be displayed after task creation.");
        Assert.assertEquals(successMessage.getText().replace("×", "").trim(), "Successfully Created", "The success message should indicate that the task was created.");

        ExtentReportManager.getTest().log(Status.PASS, "Task creation test passed");
        logger.info("Task creation test passed");

        // Navigate back to the task search page and verify the created task
        taskSearchPage = navigateToTaskPage();
        taskSearchPage.enterTitle(taskTitle);
        taskSearchPage.clickSearchButton();

        WebElement taskRow = taskSearchPage.waitForTaskRow(taskTitle, wait);
        Assert.assertNotNull(taskRow, "The created task should be present in the search results.");
        
        String description = taskSearchPage.getTaskDescription(taskRow);
        Assert.assertEquals(description, taskDescription, "The description should match.");

        String dueDate = taskSearchPage.getTaskDueDate(taskRow);
        Assert.assertEquals(dueDate, taskDueDate, "The due date should match.");

        String completed = taskSearchPage.getTaskCompletedStatus(taskRow);
        Assert.assertEquals(completed, "No", "The task should not be completed.");

        Assert.assertTrue(taskSearchPage.hasEditButton(taskRow), "The edit button should be present.");
        Assert.assertTrue(taskSearchPage.hasDeleteButton(taskRow), "The delete button should be present.");

        ExtentReportManager.getTest().log(Status.PASS, "Task verification test passed");
        logger.info("Task verification test passed");
    }

    
    
}
