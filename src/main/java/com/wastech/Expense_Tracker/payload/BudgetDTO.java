package com.wastech.Expense_Tracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {

    private Long budgetId;
    private Long categoryId;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private BigDecimal totalExpenses;
    private BigDecimal percentageSpent;
    private BigDecimal remainingBudget;
}
