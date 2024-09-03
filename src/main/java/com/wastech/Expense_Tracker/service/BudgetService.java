package com.wastech.Expense_Tracker.service;

import com.wastech.Expense_Tracker.payload.BudgetDTO;
import com.wastech.Expense_Tracker.model.Budget;
import com.wastech.Expense_Tracker.model.User;

import java.util.List;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO, User user);
    List<BudgetDTO> getBudgets();
    BudgetDTO getBudgetById(Long budgetId);
    List<BudgetDTO> getUserBudgets(User user);
    List<BudgetDTO> getBudgetsByUserAndCategory(User user, Long categoryId);
    BudgetDTO updateBudgetById(Long budgetId, BudgetDTO budgetDTO);
    String deleteBudgetById(Long budgetId);
}
