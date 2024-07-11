package com.taskmanagerplus.tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.taskmanagerplus.pages.TaskSearchPage;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.utils.ExcelUtils;
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

	private ExcelUtils excelUtils;
	
    private TaskSearchPage taskSearchPage;

    @BeforeClass
    public void setUpClass() {
    	
        // Initialize ExcelUtils with the path to the LoginCredentials.xlsx file
        excelUtils = new ExcelUtils("testdata/LoginCredentials.xlsx");
        System.out.println("ExcelUtils initialized with file: testdata/LoginCredentials.xlsx");
    	
        // Read credentials from Excel file
        String username = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Username");
        String password = excelUtils.getCellDataByColumnName("LoginCredentials", 1, "Password");
    	
        // Perform login before navigating to the Task Search Page
        performLogin(username, password);
        taskSearchPage = navigateToTaskPage();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Task Search Page");
    }

    @Test
    public void testSearchTask() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: testSearchTask");
        taskSearchPage.enterTitle("Task 1");
        taskSearchPage.enterDescription("Description for Task 1");
        taskSearchPage.clickSearchButton();

        // Add assertions to verify the search results
        wait.until(ExpectedConditions.visibilityOf(taskSearchPage.getTaskRow("Task 1")));
        Assert.assertTrue(taskSearchPage.isTaskPresent("Task 1"), "Task 1 should be present in the search results.");

        ExtentReportManager.getTest().log(Status.PASS, "Task search test passed");
    }

    @Test
    public void testCreateNewTask() {
        ExtentReportManager.getTest().log(Status.INFO, "Starting test: testCreateNewTask");
        taskSearchPage.clickCreateTaskButton();
        
        // Add assertions to verify navigation to the create task page
        Assert.assertTrue(driver.getCurrentUrl().contains("/task/new"), "Should navigate to the create task page.");

        ExtentReportManager.getTest().log(Status.PASS, "Create new task navigation test passed");
    }

    // Add more tests for other interactions and validations

    // Cleanup method is managed by BaseTest
}