package com.expense_tracker.Services.Expense;

import java.util.List;
import java.util.Map;

import com.expense_tracker.DTO.ExpenseDTO;
import com.expense_tracker.Entity.Expense;

public interface ExpenseService {


    Expense postExpense(ExpenseDTO expenseDTO);

    List<Expense> getAllExpenses();

    Expense getExpenseById(long id);

    Expense updatExpense(long id, ExpenseDTO expenseDTO);

    void deleteExpense(long id);

    Map<String, Double> getExpensesByCategory();


}
