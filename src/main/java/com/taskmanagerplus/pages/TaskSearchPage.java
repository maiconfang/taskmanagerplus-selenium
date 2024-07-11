package com.taskmanagerplus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object class for the task search page in the Task Manager Plus application.
 * 
 * <p>This class provides methods to interact with the task search page elements, 
 * including entering search criteria and performing actions on the task grid.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * TaskSearchPage taskSearchPage = new TaskSearchPage(driver);
 * taskSearchPage.enterTitle("Task 1");
 * taskSearchPage.clickSearchButton();
 * taskSearchPage.clickEditTask(1);
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be used to encapsulate the interactions 
 * with the task search page elements, ensuring that tests remain clean and maintainable.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-11
 * Version: 1.0
 */
public class TaskSearchPage {
    WebDriver driver;

    @FindBy(id = "task-search-title") // ID of the title input field
    WebElement titleInput;

    @FindBy(id = "task-search-description") // ID of the description input field
    WebElement descriptionInput;

    @FindBy(id = "task-search-btn-consult-records") // ID of the consult records button
    WebElement searchButton;

    @FindBy(id = "task-search-btn-create-record") // ID of the create new task button
    WebElement createTaskButton;

    // Add locators for other fields like due date, completed checkbox, and grid elements

    /**
     * Constructor to initialize the WebDriver and page elements.
     * 
     * <p>This constructor sets the WebDriver instance and initializes the web 
     * elements using the PageFactory.</p>
     * 
     * @param driver the WebDriver instance to be used by this page object
     */
    public TaskSearchPage(WebDriver driver) {
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
     * Clicks the search button to consult the records.
     */
    public void clickSearchButton() {
        searchButton.click();
    }

    /**
     * Clicks the create task button to navigate to the new task page.
     */
    public void clickCreateTaskButton() {
        createTaskButton.click();
    }

    // Add methods to interact with other elements like due date, completed checkbox, and grid actions
    
    /**
     * Gets the WebElement of the row corresponding to the specified task title.
     * 
     * @param taskTitle the title of the task
     * @return the WebElement of the task row
     */
    public WebElement getTaskRow(String taskTitle) {
        return driver.findElement(By.xpath("//td[@id='task-search-column-task-title' and text()='" + taskTitle + "']/parent::tr"));
    }

    /**
     * Checks if the specified task is present in the search results.
     * 
     * @param taskTitle the title of the task
     * @return true if the task is present, false otherwise
     */
    public boolean isTaskPresent(String taskTitle) {
        try {
            getTaskRow(taskTitle);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
