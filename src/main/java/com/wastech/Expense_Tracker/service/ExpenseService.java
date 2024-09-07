package com.wastech.Expense_Tracker.service;

import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    ExpenseDTO createExpense(ExpenseDTO expenseDTO, User user);

    List<ExpenseDTO> getExpenses();

    ExpenseDTO getExpenseById(Long expenseId);

    List<ExpenseDTO> getUserExpenses(User user);

    List<ExpenseDTO> getExpensesByUserAndCategory(User user, Long categoryId);

    ExpenseDTO updateExpenseById(Long incomeId, ExpenseDTO incomeDTO);

    String deleteIncomeById(Long incomeId);

    BigDecimal getTotalExpensesForMonth(User user, LocalDate startDate, LocalDate endDate);

//    List<Expense> getExpensesByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);


    // Method to calculate total monthly category expenses and compare with the budget
    Map<String, Object> calculateMonthlyCategoryExpensesWithBudget(User user, LocalDate startDate, LocalDate endDate);

    // Other expense-related methods, such as creating, updating, deleting expenses, etc.
//    List<Map<String, Object>> getMonthlyCategoryExpenses(User user, LocalDate startDate, LocalDate endDate);

//    void createExpense(ExpenseDTO expenseDTO);
}
