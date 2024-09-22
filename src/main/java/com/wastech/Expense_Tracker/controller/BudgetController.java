package com.wastech.Expense_Tracker.controller;

import com.wastech.Expense_Tracker.payload.BudgetDTO;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.service.BudgetService;
import com.wastech.Expense_Tracker.service.ExpenseService;
import com.wastech.Expense_Tracker.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BudgetController {
    @Autowired
    AuthUtil authUtil;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/budgets")
    public BudgetDTO createBudget(@RequestBody BudgetDTO budgetDTO) {
        User user = authUtil.loggedInUser();
        System.out.println("user" +user);
        return budgetService.createBudget(budgetDTO, user);
    }

    @GetMapping
    public List<BudgetDTO> getBudgets() {
        return budgetService.getBudgets();
    }

    @GetMapping("/{id}")
    public BudgetDTO getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id);
    }

    @GetMapping("/user/{userId}")
    public List<BudgetDTO> getUserBudgets(@PathVariable Long userId) {
        User user = authUtil.loggedInUser();
        return budgetService.getUserBudgets(user);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public List<BudgetDTO> getBudgetsByUserAndCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        User user = authUtil.loggedInUser();
        return budgetService.getBudgetsByUserAndCategory(user, categoryId);
    }

    @PutMapping("/{id}")
    public BudgetDTO updateBudgetById(@PathVariable Long id, @RequestBody BudgetDTO budgetDTO) {
        return budgetService.updateBudgetById(id, budgetDTO);
    }

    @GetMapping("/monthly-category-expenses")
    public ResponseEntity<Map<String, Object>> getMonthlyCategoryExpensesWithBudget(
        @AuthenticationPrincipal User user,
        @RequestParam int year,
        @RequestParam int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        Map<String, Object> response = expenseService.calculateMonthlyCategoryExpensesWithBudget(user, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public String deleteBudgetById(@PathVariable Long id) {
        return budgetService.deleteBudgetById(id);
    }
}
