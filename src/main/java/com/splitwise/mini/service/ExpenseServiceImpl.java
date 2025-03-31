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

    /**
     * Adds a new expense, assigns it to a team, and splits the amount equally among involved members.
     * @param expenseDTO The expense details provided in the request.
     * @return The saved expense details as a DTO.
     */
    @Override
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
        System.out.println("Received Expense DTO: " + expenseDTO);

        // ✅ Find the payer (the user who paid for the expense)
        Users payer = usersRepository.findById(expenseDTO.getPaidBy())
                .orElseThrow(() -> new RuntimeException("Payer not found"));
        System.out.print("playes");

        // ✅ Find the team associated with this expense
        Team team = teamRepository.findById(expenseDTO.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        
        System.out.print("Team");

        // ✅ Fetch all involved members using their emails
        List<Users> members = usersRepository.findByEmailIn(expenseDTO.getInvolvedMembers());
        
        System.out.print("members");

        if (members.isEmpty()) { 
            throw new RuntimeException("No valid users found for the provided emails.");
        }

       
        // ✅ Create and save the new expense
        Expense expense = new Expense(); 
        expense.setExpenseName(expenseDTO.getExpenseName());
        expense.setTeam(team);
        expense.setPaidBy(payer);
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription()); 
        
        Expense savedExpense = expenseRepository.save(expense);
        

        // ✅ Split the total amount equally among all involved members
        BigDecimal splitAmount = expenseDTO.getAmount()
                .divide(new BigDecimal(members.size()), 2, RoundingMode.HALF_UP);

        // ✅ Create and save expense splits for each member
        List<ExpenseSplit> expenseSplits = members.stream()
                .map(member -> new ExpenseSplit(savedExpense, member, splitAmount))
                .collect(Collectors.toList());

        expenseSplitRepository.saveAll(expenseSplits);

        // ✅ Convert saved expense splits into DTOs
        List<ExpenseSplitDTO> splitDTOs = expenseSplits.stream()
                .map(split -> new ExpenseSplitDTO(
                        split.getSplitId(), 
                        split.getExpense().getExpenseId(), 
                        split.getUser().getUserId(), 
                        split.getAmount()))
                .collect(Collectors.toList());

        // ✅ Return the response with saved expense details
        return new ExpenseDTO(
                savedExpense.getExpenseId(), 
                savedExpense.getTeam().getTeamId(), 
                savedExpense.getPaidBy().getUserId(), 
                savedExpense.getAmount(), 
                savedExpense.getDescription(), 
                savedExpense.getExpenseName(),
                members.stream().map(Users::getEmail).collect(Collectors.toList()));
    }
    
    /**
     * Retrieves all expenses associated with a given team.
     * @param teamId The ID of the team.
     * @return A list of expense DTOs for the specified team.
     */
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
    
    /**
     * Retrieves details of a specific expense by its ID.
     * @param expenseId The ID of the expense.
     * @return The expense details as a DTO.
     */
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
        );
    }
}
