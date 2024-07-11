/**
 * Utility class for browser-related operations in the Task Manager Plus application.
 * 
 * <p>This class provides various utility methods to interact with and manipulate 
 * browser settings and behaviors. It is intended to centralize common browser 
 * operations that can be reused across different test classes.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Example future method usage
 * BrowserUtils.scrollToElement(driver, element);
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be extended with static methods to perform 
 * common browser actions, such as scrolling, taking screenshots, handling alerts, etc.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
package com.taskmanagerplus.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import com.aventstack.extentreports.Status;

import com.taskmanagerplus.reports.ExtentReportManager;

import java.util.Date;

public class BrowserUtils {
    public static void captureConsoleLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            // Add logs to the report
            ExtentReportManager.getTest().log(Status.INFO, new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }
}

