package com.expense_tracker.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data

public class ExpenseDTO {
    private long id;
    private String title;
    private String description;
    private String category;
    private LocalDate date;
    private int amount;
}
