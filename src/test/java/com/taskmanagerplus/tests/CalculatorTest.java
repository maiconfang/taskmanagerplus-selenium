package com.taskmanagerplus.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
	
	
	public static int add(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
	
    @Test
    public void
    our_calculator_should_add_2_numbers() {
        Assertions.assertEquals(5, add(2, 3));
    }
}