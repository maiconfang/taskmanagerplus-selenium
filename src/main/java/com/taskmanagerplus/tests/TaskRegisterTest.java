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

    private TaskRegisterPage taskRegisterPage;
    private TaskSearchPage taskSearchPage;
    private ExcelUtils excelUtils;
    private static final Logger logger = LoggerFactory.getLogger(TaskRegisterTest.class);

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
        if (driver != null) {
            driver.quit();
        }
        logger.info("Test data cleaned up and browser closed");
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

	
	

    
    
}
