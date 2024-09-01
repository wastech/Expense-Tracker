package com.wastech.Expense_Tracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {

    private Long incomeId;
    private String source;
    private BigDecimal amount;
    private String description;
    private Date date;

}
