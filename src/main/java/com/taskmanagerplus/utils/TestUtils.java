package com.taskmanagerplus.utils;

import java.time.LocalDate;

/**
 * Utility class for common test-related operations in the Task Manager Plus application.
 * 
 * <p>This class provides various utility methods that can be used across different test classes
 * to perform common tasks such as retrieving the current date.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * LocalDate currentDate = TestUtils.getCurrentDate();
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be extended with static methods to perform 
 * common actions that are needed across multiple test cases.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */

public class TestUtils {

    /**
     * Gets the current date.
     * 
     * <p>This method returns the current date using the system clock in the default time zone.</p>
     * 
     * @return the current date as a {@link LocalDate}
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
