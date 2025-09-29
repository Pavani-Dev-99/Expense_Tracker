package com.expense_tracker.DTO;

import com.expense_tracker.Entity.Expense;
import com.expense_tracker.Entity.Income;

import lombok.Data;

@Data
public class StatsDTO {

    private double income;
    private double expense;
    private Income latestIncome;
    private Expense latestExpense;

    private double balance;
    
    private double minIncome;
    private double maxIncome;

    private double minExpense;
    private double maxExpense;

}
