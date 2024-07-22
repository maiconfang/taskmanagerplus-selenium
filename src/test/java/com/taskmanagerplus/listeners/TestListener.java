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

import com.aventstack.extentreports.Status;
import com.taskmanagerplus.reports.ExtentReportManager;
import com.taskmanagerplus.tests.BaseTest;
import com.taskmanagerplus.utils.BrowserUtils;
import com.taskmanagerplus.utils.ScreenshotUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class TestListener implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        String methodName = context.getTestMethod().map(method -> method.getName()).orElse("Unknown");
        ExtentReportManager.getTest().log(Status.PASS, "Test passed: " + methodName);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String methodName = context.getTestMethod().map(method -> method.getName()).orElse("Unknown");
        ExtentReportManager.getTest().log(Status.FAIL, "Test failed: " + methodName);
        ExtentReportManager.getTest().log(Status.FAIL, cause);

        // Obtain the WebDriver instance from the test class
        WebDriver driver = getWebDriver(context);

        if (driver != null) {
            // Capture console logs
            ExtentReportManager.getTest().log(Status.INFO, "Console Logs: ");
            BrowserUtils.captureConsoleLogs(driver);

            // Capture screenshot
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, methodName);
            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath, "Test Failure Screenshot");

            // Adds the page source to the report
            try {
                String pageSourcePath = "test-output/pagesource-" + methodName + ".html";
                Files.write(new File(pageSourcePath).toPath(), driver.getPageSource().getBytes());
                ExtentReportManager.getTest().log(Status.INFO, "Page Source at failure: ").addScreenCaptureFromPath(pageSourcePath);
            } catch (IOException e) {
                ExtentReportManager.getTest().log(Status.WARNING, "Failed to save page source: " + e.getMessage());
            }
        }
    }

    public void testSkipped(ExtensionContext context, Optional<String> reason) {
        String methodName = context.getTestMethod().map(method -> method.getName()).orElse("Unknown");
        ExtentReportManager.getTest().log(Status.SKIP, "Test skipped: " + methodName);
        reason.ifPresent(r -> ExtentReportManager.getTest().log(Status.SKIP, r));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String methodName = context.getTestMethod().map(method -> method.getName()).orElse("Unknown");
        ExtentReportManager.getTest().log(Status.SKIP, "Test aborted: " + methodName);
        ExtentReportManager.getTest().log(Status.SKIP, cause);
    }

    private WebDriver getWebDriver(ExtensionContext context) {
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseTest) {
            return ((BaseTest) testInstance).driver;
        }
        return null;
    }
}
