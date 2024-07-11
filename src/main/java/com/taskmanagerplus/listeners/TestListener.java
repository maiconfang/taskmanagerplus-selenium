/**
 * Listener class for TestNG tests that integrates with ExtentReports.
 * 
 * <p>This class implements the {@link org.testng.ITestListener} interface to 
 * provide custom behavior for test start, success, failure, and skip events.
 * It uses {@link com.taskmanagerplus.reports.ExtentReportManager} to log these 
 * events to ExtentReports.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @Listeners(com.taskmanagerplus.listeners.TestListener.class)
 * public class MyTest {
 *     // test methods
 * }
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This listener needs to be registered with TestNG either 
 * through the {@code @Listeners} annotation or the testng.xml configuration file.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
package com.taskmanagerplus.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.tests.BaseTest;
import com.taskmanagerplus.utils.BrowserUtils;
import com.taskmanagerplus.utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((BaseTest) result.getInstance()).driver;
        
        ExtentReportManager.getTest().log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());

        // Capture console logs
        ExtentReportManager.getTest().log(Status.INFO, "Console Logs: ");
        BrowserUtils.captureConsoleLogs(driver);

        // Capture screenshot
        String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getMethod().getMethodName());
        ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath, "Test Failure Screenshot");

        // Adds the page source to the report
        try {
            String pageSourcePath = "test-output/pagesource-" + result.getMethod().getMethodName() + ".html";
            Files.write(new File(pageSourcePath).toPath(), driver.getPageSource().getBytes());
            ExtentReportManager.getTest().log(Status.INFO, "Page Source at failure: ").addScreenCaptureFromPath(pageSourcePath);
        } catch (IOException e) {
            ExtentReportManager.getTest().log(Status.WARNING, "Failed to save page source: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // This method is not commonly used.
    }

    @Override
    public void onStart(ITestContext context) {
        // Do nothing
    }

    @Override
    public void onFinish(ITestContext context) {
        // Do nothing
    }
}
