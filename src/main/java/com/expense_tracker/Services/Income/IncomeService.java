package com.expense_tracker.Services.Income;

import java.util.List;

import org.springframework.stereotype.Service;

import com.expense_tracker.DTO.IncomeDTO;
import com.expense_tracker.Entity.Income;

@Service
public interface IncomeService {
    Income postIncome(IncomeDTO incomeDTO);
    List<IncomeDTO> getAllIncomes();
    Income updateIncome(long id, IncomeDTO incomeDTO);
    Income getIncomeById(long id);
    void deleteIncome(long id);
}