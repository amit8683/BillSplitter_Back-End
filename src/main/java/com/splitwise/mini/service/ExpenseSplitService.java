package com.splitwise.mini.service;

import java.util.List;

import com.splitwise.mini.dto.ExpenseSplitDTO;

public interface ExpenseSplitService {
	List<ExpenseSplitDTO> getExpenseSplitsByExpenseId(Integer expenseId);
    void updateSplitStatus(Integer splitId, String status);

}
