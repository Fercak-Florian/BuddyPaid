package com.paymybuddy.buddypaid.service;

import com.paymybuddy.buddypaid.model.Operation;

public interface IOperationService {
	public Operation addOperation(int currentUserId, int beneficiary, float amount);
}
