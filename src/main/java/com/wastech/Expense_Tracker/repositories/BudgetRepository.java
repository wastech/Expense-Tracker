package com.wastech.Expense_Tracker.repositories;

import com.wastech.Expense_Tracker.model.Budget;
import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserAndCategory(User user, Category category);
    List<Budget> findByUser(User user);

    Optional<Budget> findByUserAndStartDateAndEndDate(User user, LocalDate startDate, LocalDate endDate);
}
