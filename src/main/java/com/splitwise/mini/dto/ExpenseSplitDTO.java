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
}
