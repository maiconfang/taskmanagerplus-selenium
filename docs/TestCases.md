# Test Cases

## Login Tests

### Test Case: Valid Login
- **ID:** TC001
- **Description:** Verify successful login with valid credentials.
- **Preconditions:** System is accessible.
- **Steps:**
  1. Navigate to login page.
  2. Read credentials from Excel file.
  3. Enter username and password.
  4. Click login button.
  5. Verify URL change to home page.
  6. Verify presence of user link and logout button on dashboard.
- **Expected Result:** Login successful, URL changes to home page, dashboard elements present.

### Test Case: Invalid Login
- **ID:** TC002
- **Description:** Verify error message with invalid login credentials.
- **Preconditions:** System is accessible.
- **Steps:**
  1. Navigate to login page.
  2. Enter invalid username and password.
  3. Click login button.
  4. Verify error message displayed.
- **Expected Result:** Error message indicating invalid login attempt.

### Test Case: Empty Credentials
- **ID:** TC003
- **Description:** Verify error messages with empty credentials.
- **Preconditions:** System is accessible.
- **Steps:**
  1. Navigate to login page.
  2. Enter long strings for username and password.
  3. Verify login button is disabled.
  4. Verify error messages for both fields.
- **Expected Result:** Login button disabled, error messages displayed for both fields.

### Test Case: Short Credentials
- **ID:** TC004
- **Description:** Verify error messages with short credentials.
- **Preconditions:** System is accessible.
- **Steps:**
  1. Navigate to login page.
  2. Enter short strings for username and password.
  3. Verify login button is disabled.
  4. Verify error messages for both fields.
- **Expected Result:** Login button disabled, error messages displayed for both fields.

## Task Registration Tests

### Test Case: Create Task with All Fields
- **ID:** TC005
- **Description:** Verify task creation with all fields filled.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task registration page.
  2. Enter title, description, due date.
  3. Check completed checkbox.
  4. Click save button.
  5. Verify success message.
- **Expected Result:** Task created successfully, success message displayed.

### Test Case: Create Task with Mandatory Fields
- **ID:** TC006
- **Description:** Verify task creation with only mandatory fields.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task registration page.
  2. Enter title, description, due date.
  3. Click save button.
  4. Verify success message.
- **Expected Result:** Task created successfully, success message displayed.

### Test Case: Back Button Navigation
- **ID:** TC007
- **Description:** Verify back button navigates to task search page.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task registration page.
  2. Click back button.
  3. Verify URL contains `/task/search`.
- **Expected Result:** Navigation to task search page.

### Test Case: Completed Checkbox Toggle
- **ID:** TC008
- **Description:** Verify functionality of completed checkbox.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task registration page.
  2. Set completed checkbox to true, verify checked.
  3. Set completed checkbox to false, verify unchecked.
- **Expected Result:** Checkbox toggles correctly.

### Test Case: Create and Verify Task
- **ID:** TC009
- **Description:** Verify task creation and presence in task search.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task registration page.
  2. Enter title, description, due date, completed status.
  3. Click save button, verify success message.
  4. Navigate to task search page, search for task.
  5. Verify task details in search results.
- **Expected Result:** Task created successfully, present in search results.

## Task Search Tests

### Test Case: Search Task with Valid Title and Description
- **ID:** TC010
- **Description:** Verify search with valid title and description.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter valid title and description.
  4. Click search button.
  5. Verify task details in search results.
- **Expected Result:** Task present in search results.

### Test Case: Search Task by Title
- **ID:** TC011
- **Description:** Verify search by task title.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task title.
  4. Click search button.
  5. Verify task details in search results.
- **Expected Result:** Task present in search results.

### Test Case: Search Task by Description
- **ID:** TC012
- **Description:** Verify search by task description.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task description.
  4. Click search button.
  5. Verify task details in search results.
- **Expected Result:** Task present in search results.

### Test Case: Search Task by Due Date
- **ID:** TC013
- **Description:** Verify search by task due date.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task due date.
  4. Click search button.
  5. Verify task details in search results.
- **Expected Result:** Task present in search results.

### Test Case: Filter Tasks by Completed Status
- **ID:** TC014
- **Description:** Verify filter by completed status.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Select completed checkbox.
  4. Click search button.
  5. Verify tasks in search results are completed.
- **Expected Result:** Only completed tasks present in search results.

### Test Case: Create Task Button Navigation
- **ID:** TC015
- **Description:** Verify navigation to task creation page.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task search page.
  2. Click create task button.
  3. Verify URL contains `/task/new`.
- **Expected Result:** Navigation to task creation page.

### Test Case: Filter Tasks by Title, Description, and Completed Status
- **ID:** TC016
- **Description:** Verify filter by title, description, and completed status.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter title, description, select completed checkbox.
  4. Click search button.
  5. Verify task details in search results.
- **Expected Result:** Task present in search results.

### Test Case: No Results Error Message
- **ID:** TC017
- **Description:** Verify error message when no results are found.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task search page.
  2. Enter non-existent criteria.
  3. Click search button.
  4. Verify error message is displayed.
- **Expected Result:** Error message displayed.

### Test Case: Pagination Functionality
- **ID:** TC018
- **Description:** Verify pagination functionality.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task title.
  4. Click search button.
  5. Navigate between pages.
  6. Verify tasks on each page.
- **Expected Result:** Pagination works correctly, tasks present on each page.

### Test Case: SQL and Script Injection Prevention
- **ID:** TC019
- **Description:** Verify system's resistance to SQL and script injection attacks.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Navigate to task search page.
  2. Enter SQL injection in title.
  3. Click search button.
  4. Verify no malicious actions executed.
  5. Enter script injection in description.
  6. Click search button.
  7. Verify no script executed.
- **Expected Result:** Proper error handling, no malicious actions executed.

### Test Case: Delete Task
- **ID:** TC020
- **Description:** Verify task deletion from the task list.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task title.
  4. Click search button.
  5. Get delete button for the specific task and click it.
  6. Confirm deletion.
  7. Verify success message.
  8. Search again to ensure task is no longer present.
- **Expected Result:** Task successfully deleted, not present in search results.

## Task Edit Tests

### Test Case: Edit and Verify Task
- **ID:** TC021
- **Description:** Verify editing and updating a task.
- **Preconditions:** User is logged in.
- **Steps:**
  1. Insert test data.
  2. Navigate to task search page.
  3. Enter task title, click search button.
  4. Click edit button for task.
  5. Enter new title, description, due date.
  6. Set completed checkbox.
  7. Click save button, verify success message.
  8. Navigate to task search page, search for task.
  9. Verify task details in search results.
- **Expected Result:** Task successfully updated, present in search results with new details.
