package com.splitwise.mini.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "expense_split")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpenseSplit {
	
	public ExpenseSplit(Expense expense, Users user, BigDecimal amount) {
	    this.expense = expense;
	    this.user = user;
	    this.amount = amount;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer splitId;
    
    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable=true)
    private String status="Pending";
}
