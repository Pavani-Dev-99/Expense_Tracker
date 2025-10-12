package com.expense_tracker.Controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.DTO.ExpenseDTO;
import com.expense_tracker.Entity.Expense;
import com.expense_tracker.Services.Expense.ExpenseService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin("*")


@RestController
@RequestMapping("/api/expenses")
// @CrossOrigin("*")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<Expense> postExpense(@RequestBody ExpenseDTO expenseDTO){
        Expense createdExpense = expenseService.postExpense(expenseDTO);
        if(createdExpense != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllExpenses(){
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable long id){
        try{
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable long id, @RequestBody ExpenseDTO expenseDTO){
        try{
            return ResponseEntity.ok(expenseService.updatExpense(id, expenseDTO));
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable long id, @RequestBody ExpenseDTO expenseDTO){
        try{
            expenseService.deleteExpense(id);
            return ResponseEntity.ok("Expense deleted successfully");
        }
        catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }



    @GetMapping("/api/expenses/category-summary")
    public ResponseEntity<Map<String, Double>> getCategorySummary() {
    Map<String, Double> categorySummary = expenseService.getExpensesByCategory();
    return ResponseEntity.ok(categorySummary);
}




}
