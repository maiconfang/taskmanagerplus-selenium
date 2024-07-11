package com.taskmanagerplus.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for taking screenshots in the Task Manager Plus application.
 * 
 * <p>This class provides a method to capture screenshots of the current state
 * of the WebDriver instance. The screenshots are saved to a specified location
 * with a given name.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * String screenshotPath = ScreenshotUtils.takeScreenshot(driver, "loginPageScreenshot");
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class uses Apache Commons IO for file operations.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */

public class ScreenshotUtils {

    /**
     * Takes a screenshot of the current state of the WebDriver instance.
     * 
     * <p>This method captures the screenshot, saves it to the specified location,
     * and returns the path to the saved screenshot file.</p>
     * 
     * @param driver the WebDriver instance
     * @param screenshotName the name to use for the saved screenshot file
     * @return the path to the saved screenshot file, or null if an error occurs
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = System.getProperty("user.dir") + "/test-output/screenshots/" + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
