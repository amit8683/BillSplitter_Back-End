package com.splitwise.mini.repository;

import com.splitwise.mini.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
	List<Expense> findByTeamTeamId(Integer teamId);
}
