package com.wastech.Expense_Tracker.repositories;



import com.wastech.Expense_Tracker.model.AppRole;
import com.wastech.Expense_Tracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
