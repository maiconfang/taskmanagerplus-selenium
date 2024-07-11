package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object class for the initial page in the Task Manager Plus application.
 * 
 * <p>This class provides methods to interact with and verify elements on the 
 * initial page, including navigation links and the system name element.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * InitialPage initialPage = new InitialPage(driver);
 * Assert.assertTrue(initialPage.isNavbarBrandPresent(), "Navbar brand is not present");
 * initialPage.clickLogin();
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be used to encapsulate the interactions 
 * with the initial page elements, ensuring that tests remain clean and maintainable.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */

public class InitialPage {
    WebDriver driver;

    @FindBy(id = "navbar-brand") // ID of the system name element
    WebElement navbarBrand;

    @FindBy(id = "navbar_home_link") // ID of the "Home" link in the menu
    WebElement navLinkHome;

    @FindBy(id = "navbar_about_link") // ID of the "About" link in the menu
    WebElement navLinkAbout;

    @FindBy(id = "navbar_project_link") // ID of the "Projects" link in the menu
    WebElement navLinkProjects;

    @FindBy(id = "navbar_contact_link") // ID of the "Contact" link in the menu
    WebElement navLinkContact;

    @FindBy(id = "navbar_automation_link") // ID of the "Automation" link in the menu
    WebElement navLinkAutomation;

    @FindBy(id = "navbar_login_link") // ID of the "Login" link in the menu
    WebElement navLinkLogin;

    /**
     * Constructor to initialize the WebDriver and page elements.
     * 
     * <p>This constructor sets the WebDriver instance and initializes the web 
     * elements using the PageFactory.</p>
     * 
     * @param driver the WebDriver instance to be used by this page object
     */
    public InitialPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the login link in the navigation menu.
     */
    public void clickLogin() {
        navLinkLogin.click();
    }

    /**
     * Verifies if the navbar brand is present on the initial page.
     * 
     * @return {@code true} if the navbar brand is displayed, otherwise {@code false}
     */
    public boolean isNavbarBrandPresent() {
        return navbarBrand.isDisplayed();
    }

    /**
     * Verifies if the "Home" link is present in the navigation menu.
     * 
     * @return {@code true} if the "Home" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkHomePresent() {
        return navLinkHome.isDisplayed();
    }

    /**
     * Verifies if the "About" link is present in the navigation menu.
     * 
     * @return {@code true} if the "About" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkAboutPresent() {
        return navLinkAbout.isDisplayed();
    }

    /**
     * Verifies if the "Projects" link is present in the navigation menu.
     * 
     * @return {@code true} if the "Projects" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkProjectsPresent() {
        return navLinkProjects.isDisplayed();
    }

    /**
     * Verifies if the "Contact" link is present in the navigation menu.
     * 
     * @return {@code true} if the "Contact" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkContactPresent() {
        return navLinkContact.isDisplayed();
    }

    /**
     * Verifies if the "Automation" link is present in the navigation menu.
     * 
     * @return {@code true} if the "Automation" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkAutomationPresent() {
        return navLinkAutomation.isDisplayed();
    }

    /**
     * Verifies if the "Login" link is present in the navigation menu.
     * 
     * @return {@code true} if the "Login" link is displayed, otherwise {@code false}
     */
    public boolean isNavLinkLoginPresent() {
        return navLinkLogin.isDisplayed();
    }
}
