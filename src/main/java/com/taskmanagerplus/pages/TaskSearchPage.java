package com.taskmanagerplus.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @FindBy(css = "input[formControlName='dueDate']") // CSS selector for the due date input field
    WebElement dueDateInput;

    @FindBy(id = "task-search-completed") // ID of the completed checkbox
    WebElement completedCheckbox;

    @FindBy(id = "task-search-not-completed") // ID of the not completed checkbox
    WebElement notCompletedCheckbox;

    @FindBy(id = "task-search-no-content-message") // ID of the no content message
    WebElement noContentMessage;

    @FindBy(id = "pagination-previous-active") // ID of the active previous page link
    WebElement paginationPrevious;

    @FindBy(id = "pagination-next-active") // ID of the active next page link
    WebElement paginationNext;

    @FindBy(xpath = "//tbody/tr[not(@id='task-search-no-content')][1]") // XPath selector for the first task row on the current page
    WebElement firstTaskRow;
    
    @FindBy(id = "toast-container")
    WebElement confirmRemoveMessage;

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
     * Gets the delete button for the specified task.
     * 
     * @param taskTitle the title of the task
     * @return the WebElement of the delete button for the task
     */
    public WebElement getDeleteButtonForTask(String taskTitle) {
        WebElement taskRow = getTaskRow(taskTitle);
        return taskRow.findElement(By.id("task-search-action-remove-task"));
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

    /**
     * Waits for the specified task to be present in the search results.
     * 
     * @param taskTitle the title of the task
     * @param wait the WebDriverWait instance to use for waiting
     * @return the WebElement of the task row
     */
    public WebElement waitForTaskRow(String taskTitle, WebDriverWait wait) {
        return wait.until(driver -> {
            try {
                return getTaskRow(taskTitle);
            } catch (NoSuchElementException e) {
                return null;
            }
        });
    }

    /**
     * Gets the title of the task from the specified row.
     * 
     * @param taskRow the WebElement of the task row
     * @return the task title
     */
    public String getTaskTitle(WebElement taskRow) {
        return taskRow.findElement(By.id("task-search-column-task-title")).getText();
    }

    /**
     * Gets the description of the task from the specified row.
     * 
     * @param taskRow the WebElement of the task row
     * @return the task description
     */
    public String getTaskDescription(WebElement taskRow) {
        return taskRow.findElement(By.id("task-search-column-task-description")).getText();
    }

    /**
     * Gets the due date of the task from the specified row.
     * 
     * @param taskRow the WebElement of the task row
     * @return the task due date
     */
    public String getTaskDueDate(WebElement taskRow) {
        return taskRow.findElement(By.id("task-search-column-task-duedate")).getText();
    }

    /**
     * Gets the completed status of the task from the specified row.
     * 
     * @param taskRow the WebElement of the task row
     * @return the task completed status
     */
    public String getTaskCompletedStatus(WebElement taskRow) {
        return taskRow.findElement(By.id("task-search-column-task-completed")).getText();
    }

    /**
     * Checks if the edit button is present in the specified task row.
     * 
     * @param taskRow the WebElement of the task row
     * @return true if the edit button is present, false otherwise
     */
    public boolean hasEditButton(WebElement taskRow) {
        try {
            taskRow.findElement(By.id("task-search-action-update-task"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if the delete button is present in the specified task row.
     * 
     * @param taskRow the WebElement of the task row
     * @return true if the delete button is present, false otherwise
     */
    public boolean hasDeleteButton(WebElement taskRow) {
        try {
            taskRow.findElement(By.id("task-search-action-remove-task"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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
     * Selects the completed checkbox.
     */
    public void selectCompletedCheckbox() {
        if (!completedCheckbox.isSelected()) {
            completedCheckbox.click();
        }
    }

    /**
     * Selects the not completed checkbox.
     */
    public void selectNotCompletedCheckbox() {
        if (!notCompletedCheckbox.isSelected()) {
            notCompletedCheckbox.click();
        }
    }

    /**
     * Gets a list of completed task rows.
     * 
     * @return a list of WebElement objects representing the completed tasks
     */
    public List<WebElement> getCompletedTasks() {
        return driver.findElements(By.xpath("//td[@id='task-search-column-task-completed' and text()='Yes']/parent::tr"));
    }

    /**
     * Gets the error message displayed when no results are found.
     * 
     * @return the WebElement of the error message
     */
    public WebElement getErrorMessage() {
        return noContentMessage;
    }

    /**
     * Clicks the previous page button in the pagination.
     */
    public void clickPaginationPrevious() {
        paginationPrevious.click();
    }

    /**
     * Clicks the next page button in the pagination.
     */
    public void clickPaginationNext() {
        paginationNext.click();
    }

    /**
     * Gets the first task on the current page.
     * 
     * @return the WebElement of the first task row on the current page
     */
    public WebElement getFirstTaskOnCurrentPage() {
        return driver.findElement(By.xpath("//tbody/tr[not(contains(@class,'no-content-message'))][1]"));
    }

    /**
     * Gets the success message displayed after a task is successfully deleted.
     *
     * @return the WebElement of the success message
     */
    public WebElement getSuccessMessage() {
        return driver.findElement(By.id("success-message"));
    }
    
    /**
     * Gets the confirm remove message displayed when the record was removed.
     * 
     * @return the WebElement of the confirm remove message
     */
    public WebElement getConfirmRemoveMessage() {
        return confirmRemoveMessage;
    }

    /**
     * Clicks the title input in the page.
     */
    public void clickTitleInput() {
        titleInput.click();
    }

    /**
     * Clicks the edit button for the specified task.
     * 
     * @param taskTitle the title of the task
     */
    public void clickEditButtonForTask(String taskTitle) {
        WebElement taskRow = getTaskRow(taskTitle);
        WebElement editButton = taskRow.findElement(By.id("task-search-action-update-task"));
        editButton.click();
    }
}
