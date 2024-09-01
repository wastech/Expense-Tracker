package com.wastech.Expense_Tracker.service;

import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.IncomeDTO;

import java.util.List;

public interface IncomeService {
    IncomeDTO createIncome(IncomeDTO incomeDTO, User user);
    List<IncomeDTO> getAllIncomes();
    IncomeDTO getIncomeById(Long incomeId);
    IncomeDTO getUserIncome(User user);
    IncomeDTO updateIncomeById(Long incomeId, IncomeDTO incomeDTO);
    IncomeDTO deleteIncomeById(Long incomeId);


}
