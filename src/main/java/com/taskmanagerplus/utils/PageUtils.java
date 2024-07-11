package com.taskmanagerplus.utils;

import org.openqa.selenium.WebDriver;

/**
 * Utility class for page-related operations in the Task Manager Plus application.
 * 
 * <p>This class provides a method to retrieve the page source of the current state
 * of the WebDriver instance.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * String pageSource = PageUtils.getPageSource(driver);
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class is intended to encapsulate common operations
 * related to web pages.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */
public class PageUtils {

    /**
     * Retrieves the page source of the current state of the WebDriver instance.
     * 
     * <p>This method captures the entire HTML source of the currently loaded page.</p>
     * 
     * @param driver the WebDriver instance
     * @return the page source as a String
     */
    public static String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }
}
