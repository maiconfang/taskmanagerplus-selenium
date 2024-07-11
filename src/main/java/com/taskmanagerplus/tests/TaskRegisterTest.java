package com.taskmanagerplus.tests;

import com.taskmanagerplus.pages.TaskRegisterPage;
import com.taskmanagerplus.reports.ExtentReportManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

/**
 * Test class for the task register functionality in the Task Manager Plus application.
 * 
 * <p>This class provides tests to verify the functionality of the task register page,
 * including entering task details, saving tasks, and verifying saved tasks.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */
public class TaskRegisterTest extends BaseTest {

    private TaskRegisterPage taskRegisterPage;

    @BeforeClass
    public void setUpClass() {
        // Navigate to the Task Register Page
        driver.get("URL of your task register page");
        taskRegisterPage = new TaskRegisterPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Task Register Page");
    }

    @Test
    public void testCreateTask() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: testCreateTask");
        taskRegisterPage.enterTitle("New Task");
        taskRegisterPage.enterDescription("Description for New Task");
        taskRegisterPage.enterDueDate("2024-07-10");
        taskRegisterPage.setCompleted(true);
        taskRegisterPage.clickSaveButton();

        // Add assertions to verify the task is saved successfully
        wait.until(ExpectedConditions.urlContains("/task"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/task"), "Should navigate back to the task page after saving.");
        
        ExtentReportManager.getTest().log(Status.PASS, "Task creation test passed");
    }

    // Add more tests for other interactions and validations

    // Cleanup method is managed by BaseTest
}
