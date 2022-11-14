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
	public Operation addOperation(int currentUserId, int beneficiary, float amount) {
		Operation operation = new Operation(currentUserId, new Date(), "versement", amount, 0, "bowling");
		return operationRepository.save(operation);
	}

}
