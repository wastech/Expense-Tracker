package com.wastech.Expense_Tracker.controller;

import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.ExpenseDTO;
import com.wastech.Expense_Tracker.service.ExpenseService;
import com.wastech.Expense_Tracker.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    AuthUtil authUtil;
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO, @RequestParam Long userId) {
        User user = authUtil.loggedInUser();
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDTO, user);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {
        List<ExpenseDTO> expenses = expenseService.getExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getUserExpenses(@PathVariable Long userId) {
        User user = authUtil.loggedInUser();
        List<ExpenseDTO> expenses = expenseService.getUserExpenses(user);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUserAndCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        User user = authUtil.loggedInUser();
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserAndCategory(user, categoryId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO updatedExpense = expenseService.updateExpenseById(id, expenseDTO);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
        String response = expenseService.deleteIncomeById(id);  // Should be deleteExpenseById method in service
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
