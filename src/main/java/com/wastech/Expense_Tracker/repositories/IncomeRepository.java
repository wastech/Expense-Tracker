package com.wastech.Expense_Tracker.repositories;

import com.wastech.Expense_Tracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
