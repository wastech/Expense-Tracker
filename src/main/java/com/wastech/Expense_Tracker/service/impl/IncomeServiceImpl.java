package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.model.Income;
import com.wastech.Expense_Tracker.model.User;
import com.wastech.Expense_Tracker.payload.IncomeDTO;
import com.wastech.Expense_Tracker.repositories.IncomeRepository;
import com.wastech.Expense_Tracker.repositories.UserRepository;
import com.wastech.Expense_Tracker.service.IncomeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
   private UserRepository userRepository;


    @Override
    public IncomeDTO createIncome(IncomeDTO incomeDTO, User user) {
        Income income = modelMapper.map(incomeDTO, Income.class);
        income.setUser(user);
        List<Income> incomesList = user.getIncomes();
        incomesList.add(income);
        user.setIncomes(incomesList);
        Income savedIncome = incomeRepository.save(income);
        return modelMapper.map(savedIncome, IncomeDTO.class);
    }

    @Override
    public List<IncomeDTO> getAllIncomes() {
        return null;
    }

    @Override
    public IncomeDTO getIncomeById(Long incomeId) {
        return null;
    }

    @Override
    public IncomeDTO getUserIncome(User user) {
        return null;
    }

    @Override
    public IncomeDTO updateIncomeById(Long incomeId, IncomeDTO incomeDTO) {
        return null;
    }

    @Override
    public IncomeDTO deleteIncomeById(Long incomeId) {
        return null;
    }
}
