package com.splitwise.mini.controller;

import com.splitwise.mini.dto.ExpenseDTO;
import com.splitwise.mini.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense") 
public class ExpenseController {

    @Autowired 
    private ExpenseService expenseService; 

    // ✅ Add a new expense
    @PostMapping("/add")
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO expenseDTO) {
    	System.out.println(expenseDTO);
        ExpenseDTO savedExpense = expenseService.addExpense(expenseDTO);
        System.out.println("END"+savedExpense);
        return ResponseEntity.ok(savedExpense);
    }
    
    @GetMapping("/team/{teamId}") 
    public ResponseEntity<List<ExpenseDTO>> getExpensesByTeam(@PathVariable Integer teamId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByTeam(teamId);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Integer expenseId) {
        ExpenseDTO expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }
 
}

