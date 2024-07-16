package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object class for handling common notification messages in the Task Manager Plus application.
 * 
 * <p>This class provides methods to interact with notification messages,
 * including retrieving success messages displayed on various pages.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-16
 * Version: 1.0
 */
public class NotificationPage {
    @SuppressWarnings("unused")
	private WebDriver driver;

    @FindBy(css = ".toast-message") // CSS selector for the notification message
    private WebElement toastMessage;
    
    @FindBy(id = "toast-container")
    WebElement toastContainer;

    public NotificationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Retrieves the text of the notification message.
     * 
     * @return the notification message text
     */
    public String getToastMessageText() {
        return toastMessage.getText();
    }
    
    
    /**
     * Gets the confirm remove message displayed when the record was removed.
     * 
     * @return the WebElement of the confirm remove message
     */
    public WebElement getConfirmRemoveMessage() {
		return toastContainer;
	}
    
    /**
     * Gets the successfully created message displayed when the record was created.
     * 
     * @return the WebElement of the confirm remove message
     */
    public WebElement getSuccessfullyCreatedMessage() {
		return toastContainer;
	}
    
    
}
