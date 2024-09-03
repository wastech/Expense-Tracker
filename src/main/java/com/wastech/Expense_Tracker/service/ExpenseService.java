package com.wastech.Expense_Tracker.service;

import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseDTO createExpense(ExpenseDTO expenseDTO, User user);
    List<ExpenseDTO> getExpenses();
    ExpenseDTO getExpenseById(Long expenseId);
    List<ExpenseDTO>  getUserExpenses(User user);
    List<ExpenseDTO>  getExpensesByUserAndCategory(User user, Long categoryId);
    ExpenseDTO updateExpenseById(Long incomeId, ExpenseDTO incomeDTO);
    String deleteIncomeById(Long incomeId);
}
