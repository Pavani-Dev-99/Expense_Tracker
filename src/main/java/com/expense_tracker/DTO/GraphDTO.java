package com.expense_tracker.DTO;

import java.util.List;

import com.expense_tracker.Entity.Expense;
import com.expense_tracker.Entity.Income;

import lombok.Data;

@Data
public class GraphDTO {

    private List<Expense> expenseList;

    private List<Income> incomeList;



}
