package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.model.Budget;
import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.CategoryDTO;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;
import com.wastech.Expense_Tracker.repositories.BudgetRepository;
import com.wastech.Expense_Tracker.repositories.CategoryRepository;
import com.wastech.Expense_Tracker.repositories.ExpenseRepository;
import com.wastech.Expense_Tracker.repositories.UserRepository;
import com.wastech.Expense_Tracker.service.BudgetService;
import com.wastech.Expense_Tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {


    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO, User user) {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expense.setUser(user);
        Expense savedExpense = expenseRepository.save(expense);
        return modelMapper.map(savedExpense, ExpenseDTO.class);
    }

    @Override
    public List<ExpenseDTO> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        return modelMapper.map(expense, ExpenseDTO.class);
    }

    @Override
    public BigDecimal getTotalExpensesForMonth(User user, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startDate, endDate);

        return expenses.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    @Override
    public List<ExpenseDTO> getUserExpenses(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        return expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserAndCategory(User user, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Expense> expenses = expenseRepository.findByUserAndCategory(user, category);
        return expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO updateExpenseById(Long expenseId, ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new RuntimeException("Expense not found"));

        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setDescription(expenseDTO.getDescription());
        existingExpense.setDate(expenseDTO.getDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return modelMapper.map(updatedExpense, ExpenseDTO.class);
    }

    @Override
    public String deleteIncomeById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
        return "Expense deleted successfully";
    }


    public Map<String, Object> calculateMonthlyCategoryExpensesWithBudget(User user, LocalDate startDate, LocalDate endDate) {
        Budget budget = budgetRepository.findByUserAndStartDateAndEndDate(user, startDate, endDate)
            .orElseThrow(() -> new RuntimeException("No budget found for this period"));

        BigDecimal totalBudget = budget.getAmount();
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
        Map<Long, List<Expense>> expensesByCategory = expenses.stream()
            .collect(Collectors.groupingBy(expense -> expense.getCategory().getCategoryId()));
        Map<String, Object> result = new HashMap<>();
        BigDecimal totalExpenses = BigDecimal.ZERO;

        List<Map<String, Object>> categoryExpensesList = new ArrayList<>();
        for (Map.Entry<Long, List<Expense>> entry : expensesByCategory.entrySet()) {
            Long categoryId = entry.getKey();
            List<Expense> categoryExpenses = entry.getValue();
            BigDecimal categoryTotal = categoryExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal percentage = categoryTotal.divide(totalBudget, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

            totalExpenses = totalExpenses.add(categoryTotal);
            BigDecimal remainingBudget = totalBudget.subtract(totalExpenses);
            Map<String, Object> categorySummary = new HashMap<>();
            categorySummary.put("categoryId", categoryId);
            categorySummary.put("categoryName", categoryExpenses.get(0).getCategory().getCategoryName());
            categorySummary.put("amountSpent", categoryTotal);
            categorySummary.put("percentage", percentage);
            categorySummary.put("remainingBudget", remainingBudget);

            categoryExpensesList.add(categorySummary);
        }

        result.put("totalBudget", totalBudget);
        result.put("totalExpenses", totalExpenses);
        result.put("remainingBudget", totalBudget.subtract(totalExpenses));
        result.put("categoryExpenses", categoryExpensesList);

        return result;
    }

}
