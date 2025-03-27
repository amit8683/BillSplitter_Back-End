package com.splitwise.mini.service;

import java.util.List;

import com.splitwise.mini.dto.ExpenseDTO;

public interface ExpenseService {
    ExpenseDTO addExpense(ExpenseDTO expenseDTO);
    List<ExpenseDTO> getExpensesByTeam(Integer teamId);
    ExpenseDTO getExpenseById(Integer expenseId);
} 