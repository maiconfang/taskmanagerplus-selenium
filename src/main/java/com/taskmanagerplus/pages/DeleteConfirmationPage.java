package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteConfirmationPage {
    @SuppressWarnings("unused")
	private WebDriver driver;

    @FindBy(id = "dialog-confirmation-yes")
    private WebElement yesButton;

    @FindBy(id = "dialog-confirmation-no")
    private WebElement noButton;

    @FindBy(id = "dialog-confirmation-message")
    private WebElement confirmationMessage;

    public DeleteConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickYesButton() {
        yesButton.click();
    }

    public void clickNoButton() {
        noButton.click();
    }

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }
}
