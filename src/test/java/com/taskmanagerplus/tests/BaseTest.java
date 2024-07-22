package com.taskmanagerplus.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.taskmanagerplus.config.ConfigReader;
import com.taskmanagerplus.drivers.DriverFactory;
import com.taskmanagerplus.pages.LoginPage;
import com.taskmanagerplus.pages.TaskSearchPage;
import com.taskmanagerplus.reports.ExtentReportManager;

/**
 * Base test class for the Task Manager Plus application.
 * 
 * <p>This class provides common setup and teardown methods for all test classes,
 * including initializing the WebDriver, setting up ExtentReports, and managing WebDriverWait.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class LoginTest extends BaseTest {
 *     @Test
 *     public void testLogin() {
 *         // Test code here
 *     }
 * }
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class uses TestNG annotations to manage test lifecycle events
 * and is designed to be extended by specific test classes.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */

//Use @ExtendWith for JUnit 5 extensions
@ExtendWith(com.taskmanagerplus.listeners.TestListener.class)
public class BaseTest {
    public WebDriver driver;
    protected WebDriverWait wait;

    @BeforeAll
    public static void beforeSuite() {
        // Initialize ExtentReports before any tests run
        ExtentReportManager.setUp();
    }

    @BeforeEach
    public void setUp() {
        // Initialize the WebDriver
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize ExtentReports with the WebDriver
        ExtentReportManager.getInstance(driver);
        // Create a test entry for the current test class
        ExtentReportManager.createTest(getClass().getSimpleName());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        // Flush the report after each test method
        ExtentReportManager.flush();
    }
    
    /**
     * Helper method to perform login
     */
    public void performLogin(String username, String password) {
        driver.get(ConfigReader.getProperty("urlPublicLogin"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterLogin(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        wait.until(ExpectedConditions.urlToBe(ConfigReader.getProperty("urlApplicationHome")));
    }

    /**
     * Helper method to navigate to the task page after login
     */
    public TaskSearchPage navigateToTaskPage() {
    	driver.get(ConfigReader.getProperty("urlApplicationTask"));
        return new TaskSearchPage(driver);
    }
    
    
}
