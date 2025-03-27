package com.splitwise.mini.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
class PaymentDTO {
    private Integer paymentId;
    private Integer expenseId; 
    private Integer paidBy;
    private Integer paidTo;
    private BigDecimal amount;
    private Integer teamId;
    private String status;
}
