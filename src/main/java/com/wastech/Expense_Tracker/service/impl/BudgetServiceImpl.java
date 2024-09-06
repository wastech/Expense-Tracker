package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.exceptions.ResourceNotFoundException;
import com.wastech.Expense_Tracker.model.Budget;
import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.BudgetDTO;
import com.wastech.Expense_Tracker.repositories.BudgetRepository;
import com.wastech.Expense_Tracker.repositories.CategoryRepository;
import com.wastech.Expense_Tracker.service.BudgetService;
import com.wastech.Expense_Tracker.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BudgetDTO createBudget(BudgetDTO budgetDTO, User user) {
        Budget budget = modelMapper.map(budgetDTO, Budget.class);
        budget.setUser(user);

        Category category = categoryRepository.findById(budgetDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", budgetDTO.getCategoryId()));
        budget.setCategory(category);

        Budget savedBudget = budgetRepository.save(budget);

        return modelMapper.map(savedBudget, BudgetDTO.class);
    }


//
//    public BudgetDTO createBudget(BudgetDTO budgetDTO, User user) {
//        Budget budget = modelMapper.map(budgetDTO, Budget.class);
//        budget.setUser(user);
//
//        Category category = categoryRepository.findById(budgetDTO.getCategoryId())
//            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", budgetDTO.getCategoryId()));
//        budget.setCategory(category);
//
//        Budget savedBudget = budgetRepository.save(budget);
//
//        // Calculate total expenses for the budget period
//        LocalDate startDate = budget.getStartDate();
//        LocalDate endDate = budget.getEndDate();
//        List<Expense> expenses = expenseService.getExpensesByUserAndDateBetween(user, startDate, endDate);
//
//        // Calculate the total expense for the category in the budget
//        BigDecimal totalExpenses = expenses.stream()
//            .filter(expense -> expense.getCategory().getCategoryId().equals(category.getCategoryId()))
//            .map(Expense::getAmount)
//            .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // Calculate the percentage spent and remaining budget
//        BigDecimal percentageSpent = savedBudget.getAmount().compareTo(BigDecimal.ZERO) > 0
//            ? totalExpenses.divide(savedBudget.getAmount(), BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
//            : BigDecimal.ZERO;
//
//        BigDecimal remainingBudget = savedBudget.getAmount().subtract(totalExpenses);
//
//        // Map the saved budget to DTO and add calculated information
//        BudgetDTO savedBudgetDTO = modelMapper.map(savedBudget, BudgetDTO.class);
//        savedBudgetDTO.setTotalExpenses(totalExpenses);
//        savedBudgetDTO.setPercentageSpent(percentageSpent);
//        savedBudgetDTO.setRemainingBudget(remainingBudget);
//
//        return savedBudgetDTO;
//    }

    @Override
    public List<BudgetDTO> getBudgets() {
        List<Budget> budgets = budgetRepository.findAll();

        return budgets.stream()
            .map(budget -> modelMapper.map(budget, BudgetDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO getBudgetById(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
            .orElseThrow(() -> new ResourceNotFoundException("Budget", "id", budgetId));

        return modelMapper.map(budget, BudgetDTO.class);
    }

    @Override
    public List<BudgetDTO> getUserBudgets(User user) {
        List<Budget> budgets = budgetRepository.findByUser(user);

        return budgets.stream()
            .map(budget -> modelMapper.map(budget, BudgetDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<BudgetDTO> getBudgetsByUserAndCategory(User user, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Budget> budgets = budgetRepository.findByUserAndCategory(user, category);

        return budgets.stream()
            .map(budget -> modelMapper.map(budget, BudgetDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO updateBudgetById(Long budgetId, BudgetDTO budgetDTO) {
        Budget existingBudget = budgetRepository.findById(budgetId)
            .orElseThrow(() -> new ResourceNotFoundException("Budget", "id", budgetId));

        existingBudget.setAmount(budgetDTO.getAmount());
        existingBudget.setStartDate(budgetDTO.getStartDate());
        existingBudget.setEndDate(budgetDTO.getEndDate());

        if (budgetDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(budgetDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", budgetDTO.getCategoryId()));
            existingBudget.setCategory(category);
        }

        Budget updatedBudget = budgetRepository.save(existingBudget);

        return modelMapper.map(updatedBudget, BudgetDTO.class);
    }

    @Override
    public String deleteBudgetById(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
            .orElseThrow(() -> new ResourceNotFoundException("Budget", "id", budgetId));

        budgetRepository.delete(budget);

        return "Budget with ID " + budgetId + " has been deleted successfully.";
    }


}
