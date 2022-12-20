package com.paymybuddy.buddypaid.service;

import com.paymybuddy.buddypaid.model.Operation;

public interface IOperationService {
	public Operation addOperation(int currentUserId, int beneficiary, double amount, String description);
	public int getDebit(int userId);
	public int getCredit(int userId);
	public void manageUserAccount(int currentUser, int amount);
}
