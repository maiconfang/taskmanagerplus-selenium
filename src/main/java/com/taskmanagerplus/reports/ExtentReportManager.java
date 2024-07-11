package com.taskmanagerplus.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.taskmanagerplus.utils.EnvironmentUtils;

import org.openqa.selenium.WebDriver;

/**
 * Utility class for managing ExtentReports in the Task Manager Plus application.
 * 
 * <p>This class provides methods to set up and manage ExtentReports, create test entries,
 * and retrieve the current test instance. It also adds environment information to the report.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * ExtentReportManager.setUp();
 * ExtentTest test = ExtentReportManager.createTest("Test Name");
 * test.log(Status.INFO, "This is a log message.");
 * ExtentReportManager.flush();
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class uses a singleton pattern to ensure only one instance of ExtentReports is created.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-10
 * Version: 1.0
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentSparkReporter htmlReporter;
    private static String reportFileName = "ExtentReport.html";
    private static String reportFilePath = System.getProperty("user.dir") + "/test-output/" + reportFileName;

    /**
     * Sets up the ExtentReports instance and configures the reporter.
     */
    public static synchronized void setUp() {
        if (extent == null) {
            htmlReporter = new ExtentSparkReporter(reportFilePath);
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("Automation Test Report");
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setReportName("Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
    }

    /**
     * Gets the singleton instance of ExtentReports.
     * 
     * @param driver the WebDriver instance
     * @return the singleton instance of {@link ExtentReports}
     */
    public static synchronized ExtentReports getInstance(WebDriver driver) {
        if (extent == null) {
            setUp();
        }
        if (driver != null) {
            // Adding environment information
            extent.setSystemInfo("OS", EnvironmentUtils.getOS());
            extent.setSystemInfo("Browser", EnvironmentUtils.getBrowserName(driver));
            extent.setSystemInfo("Browser Version", EnvironmentUtils.getBrowserVersion(driver));
        }
        return extent;
    }

    /**
     * Creates a new test entry in the ExtentReports instance.
     * 
     * @param testName the name of the test
     * @return the created {@link ExtentTest} instance
     */
    public static synchronized ExtentTest createTest(String testName) {
        test = getInstance(null).createTest(testName);
        return test;
    }

    /**
     * Gets the current ExtentTest instance.
     * 
     * @return the current {@link ExtentTest} instance
     * @throws IllegalStateException if no test has been created
     */
    public static synchronized ExtentTest getTest() {
        if (test == null) {
            throw new IllegalStateException("ExtentTest is not initialized. Call createTest() before getTest().");
        }
        return test;
    }

    /**
     * Flushes the ExtentReports instance, writing all logs and information to the report.
     */
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
