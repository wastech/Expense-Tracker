package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.model.Budget;
import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.CategoryDTO;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;
import com.wastech.Expense_Tracker.repositories.CategoryRepository;
import com.wastech.Expense_Tracker.repositories.ExpenseRepository;
import com.wastech.Expense_Tracker.repositories.UserRepository;
import com.wastech.Expense_Tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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




}
