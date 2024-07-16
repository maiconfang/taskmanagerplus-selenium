package com.taskmanagerplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object class for the task register page in the Task Manager Plus application.
 * 
 * <p>This class provides methods to interact with the task register page elements, 
 * including entering task details and saving the task.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * TaskRegisterPage taskRegisterPage = new TaskRegisterPage(driver);
 * taskRegisterPage.enterTitle("New Task");
 * taskRegisterPage.enterDescription("Task Description");
 * taskRegisterPage.enterDueDate("2024-07-09");
 * taskRegisterPage.clickSaveButton();
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be used to encapsulate the interactions 
 * with the task register page elements, ensuring that tests remain clean and maintainable.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */
public class TaskRegisterPage {
    WebDriver driver;

    @FindBy(id = "task-register-title") // ID of the title input field
    WebElement titleInput;

    @FindBy(id = "task-register-description") // ID of the description input field
    WebElement descriptionInput;
    
    @FindBy(id = "task-register-duedate") // ID of the dueDate input field
    WebElement dueDateInput;

    @FindBy(id = "task-register-completed") // ID of the completed checkbox
    WebElement completedCheckbox;


    @FindBy(id = "task-register-btn-save-task") // ID of the save button
    WebElement saveButton;

    @FindBy(id = "task-register-btn-back") // ID of the back button
    WebElement backButton;


    // Add locators for other fields like due date

    /**
     * Constructor to initialize the WebDriver and page elements.
     * 
     * <p>This constructor sets the WebDriver instance and initializes the web 
     * elements using the PageFactory.</p>
     * 
     * @param driver the WebDriver instance to be used by this page object
     */
    public TaskRegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Enters the specified title in the title input field.
     * 
     * @param title the title to be entered
     */
    public void enterTitle(String title) {
        titleInput.sendKeys(title);
    }

    /**
     * Enters the specified description in the description input field.
     * 
     * @param description the description to be entered
     */
    public void enterDescription(String description) {
        descriptionInput.sendKeys(description);
    }

    /**
     * Enters the specified due date in the due date input field.
     * 
     * @param dueDate the due date to be entered
     */
    public void enterDueDate(String dueDate) {
    	dueDateInput.sendKeys(dueDate);
    }

    /**
     * Checks or unchecks the completed checkbox based on the given value.
     * 
     * @param completed true to check the checkbox, false to uncheck
     */
    public void setCompleted(boolean completed) {
        if (completed && !completedCheckbox.isSelected()) {
            completedCheckbox.click();
        } else if (!completed && completedCheckbox.isSelected()) {
            completedCheckbox.click();
        }
    }

    /**
     * Clicks the save button to save the task.
     */
    public void clickSaveButton() {
        saveButton.click();
    }

    /**
     * Clicks the back button to navigate back to the task search page.
     */
    public void clickBackButton() {
        backButton.click();
    }

    /**
     * Retrieves the save button element.
     * 
     * @return the WebElement representing the save button
     */
    public WebElement getSaveButton() {
        return saveButton;
    }
    
    /**
     * Clicks the title input field.
     */
    public void clickTitleInput() {
        titleInput.click();
    }
    
    /**
     * Checks if the completed checkbox is selected.
     * 
     * @return true if the completed checkbox is selected, false otherwise
     */
    public boolean isCompletedChecked() {
        return completedCheckbox.isSelected();
    }
}
