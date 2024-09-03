package com.wastech.Expense_Tracker.controller;

import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.IncomeDTO;
import com.wastech.Expense_Tracker.service.IncomeService;
import com.wastech.Expense_Tracker.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class IncomeController {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    IncomeService incomeService;

    @PostMapping("/incomes")
    public ResponseEntity<IncomeDTO> createAddress(@Valid @RequestBody IncomeDTO incomeDTO) {
        User user = authUtil.loggedInUser();
        IncomeDTO savedIncomeDTO = incomeService.createIncome(incomeDTO, user);
        return new ResponseEntity<>(savedIncomeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDTO>> getAddresses() {
        List<IncomeDTO> incomeList = incomeService.getAllIncomes();
        return new ResponseEntity<>(incomeList, HttpStatus.OK);
    }


    @GetMapping("/incomes/{incomeId}")
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable Long incomeId) {
        IncomeDTO incomeDTO = incomeService.getIncomeById(incomeId);
        return new ResponseEntity<>(incomeDTO, HttpStatus.OK);
    }

    @GetMapping("/users/incomes")
    public ResponseEntity<List<IncomeDTO>> getUserIncomes() {
        User user = authUtil.loggedInUser();
        List<IncomeDTO> incomeList = incomeService.getUserIncome(user);
        return new ResponseEntity<>(incomeList, HttpStatus.OK);
    }

    @PutMapping("/incomes/{incomeId}")
    public ResponseEntity<IncomeDTO> updateIncome(@PathVariable Long incomeId
        , @RequestBody IncomeDTO incomeDTO) {
        IncomeDTO updatedIncome = incomeService.updateIncomeById(incomeId, incomeDTO);
        return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
    }

    @DeleteMapping("/incomes/{incomeId}")
    public ResponseEntity<String> updateIncome(@PathVariable Long incomeId) {
        String status = incomeService.deleteIncomeById(incomeId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
