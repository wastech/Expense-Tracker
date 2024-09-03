package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.exceptions.ResourceNotFoundException;
import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;
import com.wastech.Expense_Tracker.repositories.CategoryRepository;
import com.wastech.Expense_Tracker.repositories.ExpenseRepository;
import com.wastech.Expense_Tracker.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO, User user) {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expense.setUser(user);

        Category category = categoryRepository.findById(expenseDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category", "expenseDTO.getCategoryId()", expenseDTO.getCategoryId()));
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);

        return modelMapper.map(savedExpense, ExpenseDTO.class);
    }

    @Override
    public List<ExpenseDTO> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

        List<ExpenseDTO> expenseDTOs = expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());

        return expenseDTOs;
    }

    @Override
    public ExpenseDTO getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));

        ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> getUserExpenses(User user) {

        List<Expense> expenses = expenseRepository.findByUser(user);

        List<ExpenseDTO> expenseDTOs = expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());

        return expenseDTOs;
    }

    @Override
    public List<ExpenseDTO> getExpensesByUserAndCategory(User user, Long categoryId) {
        // Find the category by its ID
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        // Fetch expenses by user and category
        List<Expense> expenses = expenseRepository.findByUserAndCategory(user, category);

        // Convert to DTOs
        List<ExpenseDTO> expenseDTOs = expenses.stream()
            .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
            .collect(Collectors.toList());

        return expenseDTOs;
    }

    @Override
    public ExpenseDTO updateExpenseById(Long expenseId, ExpenseDTO expenseDTO) {

        Expense existingExpense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));

        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setDescription(expenseDTO.getDescription());
        existingExpense.setDate(expenseDTO.getDate());

        if (expenseDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", expenseDTO.getCategoryId()));
            existingExpense.setCategory(category);
        }

        Expense updatedExpense = expenseRepository.save(existingExpense);

        return modelMapper.map(updatedExpense, ExpenseDTO.class);
    }

    @Override
    public String deleteIncomeById(Long incomeId) {
        Expense expense = expenseRepository.findById(incomeId)
            .orElseThrow(() -> new ResourceNotFoundException("Income", "id", incomeId));

        expenseRepository.delete(expense);

        return "Income with ID " + incomeId + " has been deleted successfully.";
    }
}
