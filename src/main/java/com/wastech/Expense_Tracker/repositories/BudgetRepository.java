package com.wastech.Expense_Tracker.repositories;

import com.wastech.Expense_Tracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}