package com.expense_tracker.Services.Stats;

import com.expense_tracker.DTO.GraphDTO;
import com.expense_tracker.DTO.StatsDTO;

public interface StatsService {

    
    GraphDTO getChartData();

    StatsDTO getStats();


}
