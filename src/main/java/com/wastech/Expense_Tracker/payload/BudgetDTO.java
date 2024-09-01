package com.wastech.Expense_Tracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {

    private Long budgetId;
    private Long categoryId;
    private BigDecimal amount;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
}
