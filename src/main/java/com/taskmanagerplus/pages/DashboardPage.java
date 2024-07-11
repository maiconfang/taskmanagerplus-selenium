package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object class for the dashboard page in the Task Manager Plus application.
 * 
 * <p>This class provides methods to interact with and verify elements on the 
 * dashboard page that is displayed after a successful login.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * DashboardPage dashboardPage = new DashboardPage(driver);
 * Assert.assertTrue(dashboardPage.isUserLinkPresent(), "User link is not present");
 * Assert.assertTrue(dashboardPage.isLogoutButtonPresent(), "Logout button is not present");
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be used to encapsulate the interactions 
 * with the dashboard page elements, ensuring that tests remain clean and maintainable.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
public class DashboardPage {
    WebDriver driver;

    @FindBy(id = "nav-link-user") // ID of the user link element on the dashboard page
    WebElement userLink;

    @FindBy(id = "btn-logout") // ID of the logout button
    WebElement logoutButton;

    /**
     * Constructor to initialize the WebDriver and page elements.
     * 
     * <p>This constructor sets the WebDriver instance and initializes the web 
     * elements using the PageFactory.</p>
     * 
     * @param driver the WebDriver instance to be used by this page object
     */
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies if the user link is present on the dashboard page.
     * 
     * @return {@code true} if the user link is displayed, otherwise {@code false}
     */
    public boolean isUserLinkPresent() {
        return userLink.isDisplayed();
    }

    /**
     * Verifies if the logout button is present on the dashboard page.
     * 
     * @return {@code true} if the logout button is displayed, otherwise {@code false}
     */
    public boolean isLogoutButtonPresent() {
        return logoutButton.isDisplayed();
    }
}
