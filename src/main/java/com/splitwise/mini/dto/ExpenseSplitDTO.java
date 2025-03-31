package com.splitwise.mini.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSplitDTO {
    private Integer splitId;
    private Integer expenseId;
    private Integer userId;
    private BigDecimal amount;
    
    public ExpenseSplitDTO(Integer splitId, Integer expenseId, Integer userId, BigDecimal amount) {
		super();
		this.splitId = splitId;
		this.expenseId = expenseId;
		this.userId = userId;
		this.amount = amount;
	}

	private String status;
} 
