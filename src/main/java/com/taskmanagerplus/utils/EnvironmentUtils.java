package com.taskmanagerplus.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Utility class for retrieving environment-related information in the Task Manager Plus application.
 * 
 * <p>This class provides methods to retrieve the operating system name, browser name,
 * and browser version of the current WebDriver instance.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * String os = EnvironmentUtils.getOS();
 * String browserName = EnvironmentUtils.getBrowserName(driver);
 * String browserVersion = EnvironmentUtils.getBrowserVersion(driver);
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class assumes that the WebDriver instance is a RemoteWebDriver.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */
public class EnvironmentUtils {

    /**
     * Retrieves the name of the operating system.
     * 
     * @return the name of the operating system
     */
    public static String getOS() {
        return System.getProperty("os.name");
    }

    /**
     * Retrieves the name of the browser being used by the WebDriver instance.
     * 
     * @param driver the WebDriver instance
     * @return the name of the browser
     */
    public static String getBrowserName(WebDriver driver) {
        return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
    }

    /**
     * Retrieves the version of the browser being used by the WebDriver instance.
     * 
     * @param driver the WebDriver instance
     * @return the version of the browser
     */
    public static String getBrowserVersion(WebDriver driver) {
        return ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
    }
}
