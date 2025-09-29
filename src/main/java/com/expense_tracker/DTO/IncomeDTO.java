package com.expense_tracker.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class IncomeDTO {

    private long id;

    private String title;
    private int amount;
    private LocalDate date;
    private String category;
    private String description;

}
