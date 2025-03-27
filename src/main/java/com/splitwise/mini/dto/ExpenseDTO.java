package com.splitwise.mini.dto;

import java.math.BigDecimal;
import java.util.List;

import com.splitwise.mini.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Integer expenseId;
    private Integer teamId;
    private Integer paidBy;
    private BigDecimal amount;
    private String description;
    private String expenseName;
    
    public ExpenseDTO(Integer expenseId, Integer teamId, Integer paidBy, BigDecimal amount, String description,
			String expenseName) {
		super();
		this.expenseId = expenseId;
		this.teamId = teamId;
		this.paidBy = paidBy;
		this.amount = amount;
		this.description = description;
		this.expenseName = expenseName;
	}

	private List<String> involvedMembers;
    ;
}

