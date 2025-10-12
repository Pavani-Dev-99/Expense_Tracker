package com.expense_tracker.Expense.Tracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {
    @Test
    public void testHelloWorld() {
        assertEquals("Hello, World!", new HelloWorldTest().greet());
    }

    private Object greet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'greet'");
    }
}