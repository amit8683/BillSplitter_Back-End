package com.splitwise.mini.service;

import com.splitwise.mini.dto.ExpenseDTO;
import com.splitwise.mini.dto.ExpenseSplitDTO;
import com.splitwise.mini.entity.Expense;
import com.splitwise.mini.entity.ExpenseSplit;
import com.splitwise.mini.entity.Team;
import com.splitwise.mini.entity.Users;
import com.splitwise.mini.repository.ExpenseRepository;
import com.splitwise.mini.repository.ExpenseSplitRepository;
import com.splitwise.mini.repository.TeamRepository;
import com.splitwise.mini.repository.UsersRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ExpenseSplitRepository expenseSplitRepository;

    @Override
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
    	System.out.print(expenseDTO);


        // Find the payer (paidBy)
        Users payer = usersRepository.findById(expenseDTO.getPaidBy())
                .orElseThrow(() -> new RuntimeException("Payer not found")); 
 
        // Find the team
        Team team = teamRepository.findById(expenseDTO.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

         //Convert involvedMembers (emails) into user entities
        List<Users> members = usersRepository.findByEmailIn(expenseDTO.getInvolvedMembers());

        if (members.isEmpty()) { 
            throw new RuntimeException("No valid users found for provided emails.");
        }

        // ✅ Include payer in the list (if not already included)
        if (!members.contains(payer)) {
            members.add(payer);
        }

        // Save the expense
        Expense expense = new Expense(); 
        expense.setExpenseName(expenseDTO.getExpenseName());
        expense.setTeam(team);
        expense.setPaidBy(payer);
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription()); 
        System.out.print(expense.getAmount());
        Expense savedExpense = expenseRepository.save(expense);
        
        System.out.println(savedExpense.getExpenseId());

        // ✅ Split amount equally among all members (including payer)
        BigDecimal splitAmount = expenseDTO.getAmount()
                .divide(new BigDecimal(members.size()), 2, RoundingMode.HALF_UP);

        // Save expense splits for each member
        List<ExpenseSplit> expenseSplits = members.stream()
                .map(member -> new ExpenseSplit(savedExpense, member, splitAmount))
                .collect(Collectors.toList());

        expenseSplitRepository.saveAll(expenseSplits);

        // Convert to DTO response
        List<ExpenseSplitDTO> splitDTOs = expenseSplits.stream()
                .map(split -> new ExpenseSplitDTO(split.getSplitId(), 
                                                  split.getExpense().getExpenseId(), 
                                                  split.getUser().getUserId(), 
                                                  split.getAmount()))
                .collect(Collectors.toList());

        return new ExpenseDTO(savedExpense.getExpenseId(), 
                              savedExpense.getTeam().getTeamId(), 
                              savedExpense.getPaidBy().getUserId(), 
                              savedExpense.getAmount(), 
                              savedExpense.getDescription(), 
                              savedExpense.getExpenseName(),
                              members.stream().map(Users::getEmail).collect(Collectors.toList()));
    }
    
    @Override
    public List<ExpenseDTO> getExpensesByTeam(Integer teamId) {
        List<Expense> expenses = expenseRepository.findByTeamTeamId(teamId);

        return expenses.stream().map(expense -> new ExpenseDTO(
                expense.getExpenseId(),
                expense.getTeam().getTeamId(),
                expense.getPaidBy().getUserId(),
                expense.getAmount(),
                expense.getDescription(), 
                expense.getExpenseName(),
                expense.getExpenseSplits().stream()
                        .map(split -> split.getUser().getEmail())
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
    
    @Override
    public ExpenseDTO getExpenseById(Integer expenseId) {
    	Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        
    	  return new ExpenseDTO(
    	            expense.getExpenseId(),
    	            expense.getTeam().getTeamId(),
    	            expense.getPaidBy().getUserId(),
    	            expense.getAmount(),
    	            expense.getDescription(),
    	            expense.getExpenseName()
    	    ); }
}
