package com.expense_tracker.Repository;
import com.expense_tracker.Entity.Expense;
import com.expense_tracker.Entity.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
// import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

        List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

        @Query("SELECT SUM(e.amount) FROM Expense e")
        Double sumAllAmounts();

        Optional<Expense> findFirstByOrderByDateDesc();

        List<Object[]> findExpensesGroupedByCategory();


}
