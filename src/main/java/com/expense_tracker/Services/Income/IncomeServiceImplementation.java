package com.expense_tracker.Services.Income;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.expense_tracker.DTO.IncomeDTO;
import com.expense_tracker.Entity.Income;
import com.expense_tracker.Repository.IncomeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImplementation implements IncomeService{

private final IncomeRepository incomeRepository;

public Income postIncome(IncomeDTO incomeDTO){
    return saveOrUpdateIncome(new Income(), incomeDTO);
}

private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO){
    income.setTitle(incomeDTO.getTitle());
    income.setAmount(incomeDTO.getAmount());
    income.setDate(incomeDTO.getDate());
    income.setCategory(incomeDTO.getCategory());
    income.setDescription(incomeDTO.getDescription());
    
    return incomeRepository.save(income);
}


public List<IncomeDTO> getAllIncomes(){
    return incomeRepository.findAll().stream()
    .sorted(Comparator.comparing(Income::getDate).reversed())
    .map(Income::getIncomeDTO)
    .collect(Collectors.toList());

}


public Income updateIncome(long id, IncomeDTO incomeDTO){
    Optional<Income> optionalIncome = incomeRepository.findById(id);
    if(optionalIncome.isPresent()){
        Income existingIncome = optionalIncome.get();
        return saveOrUpdateIncome(existingIncome, incomeDTO);
    }
    else{
        throw new EntityNotFoundException("Entity is not present with id: " + id);
    }
}


public Income getIncomeById(long id){
    Optional<Income> optionalIncome = incomeRepository.findById(id);
    if(optionalIncome.isPresent()){
        return optionalIncome.get();
    }
    else{
        throw new EntityNotFoundException("Entity is not present with id: " + id);
    }
}


public void deleteIncome(long id){
    Optional<Income> optionalIncome = incomeRepository.findById(id);
    if(optionalIncome.isPresent()){
        incomeRepository.deleteById(id);
    }
    else{
        throw new EntityNotFoundException("Entity is not present with id: " + id);
    }
}

}


