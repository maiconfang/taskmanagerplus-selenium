/**
 * Factory class for creating WebDriver instances.
 * 
 * <p>This class is responsible for setting up the WebDriver for Chrome browser 
 * and providing a method to obtain the WebDriver instance. The path to the 
 * ChromeDriver executable is read from the configuration properties file.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * WebDriver driver = DriverFactory.getDriver();
 * }
 * </pre>
 * 
 * <p><b>Note:</b> Ensure that the ChromeDriver executable path is correctly set 
 * in the "config.properties" file under the key "chromeDriverPath".</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
package com.taskmanagerplus.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.taskmanagerplus.config.ConfigReader;

public class DriverFactory {

    /**
     * Gets a new instance of WebDriver for Chrome.
     * 
     * <p>This method sets the system property for the ChromeDriver executable 
     * path using the value from the configuration properties file and then 
     * returns a new instance of ChromeDriver.</p>
     * 
     * @return a new instance of {@link org.openqa.selenium.WebDriver} for Chrome
     */
    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getProperty("chromeDriverPath"));
        return new ChromeDriver();
    }
}
