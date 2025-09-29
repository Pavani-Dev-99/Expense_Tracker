package com.expense_tracker.Entity;

import java.time.LocalDate;

import com.expense_tracker.DTO.IncomeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private int amount;
    private LocalDate date;
    private String category;
    private String description;


    @JsonIgnore
    public IncomeDTO getIncomeDTO() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setId((int) this.id);
        incomeDTO.setTitle(this.title);
        incomeDTO.setAmount(this.amount);
        incomeDTO.setDate(this.date);
        incomeDTO.setCategory(this.category);
        incomeDTO.setDescription(this.description);

        return incomeDTO;
    }

}
