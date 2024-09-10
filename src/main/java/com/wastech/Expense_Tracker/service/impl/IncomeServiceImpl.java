package com.wastech.Expense_Tracker.service.impl;

import com.wastech.Expense_Tracker.exceptions.ResourceNotFoundException;
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
        List<Income> incomes = incomeRepository.findAll();
        return incomes.stream().map(income -> modelMapper.map(income, IncomeDTO.class)).toList();
    }

    @Override
    public IncomeDTO getIncomeById(Long incomeId) {
        Income income = incomeRepository.findById(incomeId)
            .orElseThrow(() -> new ResourceNotFoundException("Income", "incomeId", incomeId));
        return modelMapper.map(income, IncomeDTO.class);
    }

    @Override
    public List<IncomeDTO> getUserIncome(User user) {
        List<Income> incomes = user.getIncomes();
        return incomes.stream().map(income -> modelMapper.map(income, IncomeDTO.class)).toList();

    }

    @Override
    public IncomeDTO updateIncomeById(Long incomeId, IncomeDTO incomeDTO) {
        Income incomeFromDatabase = incomeRepository.findById(incomeId)
            .orElseThrow(() -> new ResourceNotFoundException("Income", "incomeId", incomeId));

        incomeFromDatabase.setSource(incomeDTO.getSource());
        incomeFromDatabase.setAmount(incomeDTO.getAmount());
        incomeFromDatabase.setDescription(incomeDTO.getDescription());
        Income updatedIncome = incomeRepository.save(incomeFromDatabase);
        User user = incomeFromDatabase.getUser();
        user.getIncomes().removeIf(income -> income.getIncomeId().equals(incomeId));
        user.getIncomes().add(updatedIncome);
        userRepository.save(user);

        return modelMapper.map(updatedIncome, IncomeDTO.class);

    }

    @Override
    public String deleteIncomeById(Long incomeId) {
        Income incomeFromDatabase = incomeRepository.findById(incomeId)
            .orElseThrow(() -> new ResourceNotFoundException("Income", "incomeId", incomeId));
        User user = incomeFromDatabase.getUser();
        user.getIncomes().removeIf(address -> address.getIncomeId().equals(incomeId));
        userRepository.save(user);
        incomeRepository.delete(incomeFromDatabase);

        return "Income deleted successfully with incomeId: " + incomeId;
    }
}