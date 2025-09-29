package com.expense_tracker.Services.Expense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.expense_tracker.DTO.ExpenseDTO;
import com.expense_tracker.Entity.Expense;
import com.expense_tracker.Repository.ExpenseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor


public class ExpenseServiceImplementation implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO){
        return saveOrUpdateExpense(new Expense(), expenseDTO);
    }
    
    private Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO) {
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expense.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        // expense.setId(expense.getId());

        return expenseRepository.save(expense);
    }

    //  @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll().stream()
        .sorted(Comparator.comparing(Expense::getDate).reversed())
        .collect(Collectors.toList());
    }

    
    public Expense getExpenseById(long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return optionalExpense.get();
        }
        else{
            throw new EntityNotFoundException("Entity is not present with is: " + id);
        }
    }

    public Expense updatExpense(long id, ExpenseDTO expenseDTO){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            Expense existingExpense = optionalExpense.get();
            return saveOrUpdateExpense(existingExpense, expenseDTO);
        }
        else{
            throw new EntityNotFoundException("Entity is not present with is: " + id);
        }
    }

    public void deleteExpense(long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            expenseRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Entity is not present with is: " + id);
        }
    }
}
