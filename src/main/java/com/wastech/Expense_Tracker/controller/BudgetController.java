package com.wastech.Expense_Tracker.controller;

import com.wastech.Expense_Tracker.payload.BudgetDTO;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.service.BudgetService;
import com.wastech.Expense_Tracker.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    AuthUtil authUtil;

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public BudgetDTO createBudget(@RequestBody BudgetDTO budgetDTO, @RequestParam Long userId) {

        User user = authUtil.loggedInUser();
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

    @DeleteMapping("/{id}")
    public String deleteBudgetById(@PathVariable Long id) {
        return budgetService.deleteBudgetById(id);
    }
}