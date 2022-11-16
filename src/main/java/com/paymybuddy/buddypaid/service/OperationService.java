package com.paymybuddy.buddypaid.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.repository.IOperationRepository;

@Service
public class OperationService implements IOperationService {

	private IOperationRepository operationRepository;

	public OperationService(IOperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@Override
	public Operation addOperation(int currentUserId, int beneficiary, float amount, String description) {
		Operation operation = new Operation(currentUserId, beneficiary, new Date(), "versement", amount, description);
		return operationRepository.save(operation);
	}

	@Override
	public int getDebit(int userId) {
		return operationRepository.sumOfDebit(userId);
	}

	@Override
	public int getCredit(int userId) {
		return operationRepository.sumOfCredit(userId);
	}
}
