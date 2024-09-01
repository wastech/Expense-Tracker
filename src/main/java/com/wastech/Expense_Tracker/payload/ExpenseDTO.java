package com.wastech.Expense_Tracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Long expenseId;
    private Long userId;
    private Long categoryId;
    private BigDecimal amount;
    private String description;
    private Date date;
    private Date createdAt;
    private Date updatedAt;
}
