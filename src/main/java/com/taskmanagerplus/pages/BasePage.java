package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Base class for all page objects in the Task Manager Plus application.
 * 
 * <p>This class provides a common constructor for initializing WebDriver 
 * and using the PageFactory to initialize web elements. All other page 
 * object classes should extend this class to inherit these functionalities.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class LoginPage extends BasePage {
 *     public LoginPage(WebDriver driver) {
 *         super(driver);
 *     }
 *     // additional methods and elements
 * }
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be extended by all page object classes 
 * to ensure that the WebDriver instance is shared and web elements are 
 * properly initialized.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
public class BasePage {
    protected WebDriver driver;

    /**
     * Constructor to initialize the WebDriver and page elements.
     * 
     * <p>This constructor sets the WebDriver instance and initializes the web 
     * elements using the PageFactory.</p>
     * 
     * @param driver the WebDriver instance to be used by this page object
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
