package com.splitwise.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.splitwise.mini.entity.ExpenseSplit;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit,Integer> {
	List<ExpenseSplit> findByExpenseExpenseId(Integer expenseId);
}
