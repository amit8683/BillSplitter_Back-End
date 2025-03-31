package com.splitwise.mini.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.splitwise.mini.dto.ExpenseSplitDTO;
import com.splitwise.mini.entity.ExpenseSplit;
import com.splitwise.mini.repository.ExpenseSplitRepository;

@Service
public class ExpenseSplitServiceImpl implements ExpenseSplitService {

    private final ExpenseSplitRepository expenseSplitRepository;

    public ExpenseSplitServiceImpl(ExpenseSplitRepository expenseSplitRepository) {
        this.expenseSplitRepository = expenseSplitRepository;
    }

    @Override
    public List<ExpenseSplitDTO> getExpenseSplitsByExpenseId(Integer expenseId) {
        List<ExpenseSplit> expenseSplits = expenseSplitRepository.findByExpenseExpenseId(expenseId);
        return expenseSplits.stream()
                .map(split -> new ExpenseSplitDTO(split.getSplitId(), split.getExpense().getExpenseId(), 
                        split.getUser().getUserId(), split.getAmount(),split.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void ChangeSplitStatusToRequested(Integer splitId) {
        ExpenseSplit split = expenseSplitRepository.findById(splitId)
                .orElseThrow(() -> new RuntimeException("Expense Split not found"));

        split.setStatus("Requested");
        expenseSplitRepository.save(split);
    }

	@Override
	public void ChangeSplitStatusToSettle(Integer splitId) {
		 ExpenseSplit split = expenseSplitRepository.findById(splitId)
	                .orElseThrow(() -> new RuntimeException("Expense Split not found"));

	        split.setStatus("Settled");
	        expenseSplitRepository.save(split);
		
	}

 

}
