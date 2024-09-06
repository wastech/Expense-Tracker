package com.wastech.Expense_Tracker.repositories;

import com.wastech.Expense_Tracker.model.Category;
import com.wastech.Expense_Tracker.model.Expense;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

    List<Expense> findByUserAndCategory(User user, Category category);


    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate")
    List<Expense> findByUserAndDateBetween(@Param("user") User user,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
}