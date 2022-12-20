package com.paymybuddy.buddypaid.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserAccount;
import com.paymybuddy.buddypaid.repository.IOperationRepository;
import com.paymybuddy.buddypaid.repository.IUserAccountRepository;
import com.paymybuddy.buddypaid.repository.IUserRepository;

@Transactional
@Service
public class OperationService implements IOperationService {

	private IOperationRepository operationRepository;
	private IUserAccountRepository userAccountRepository;

	public OperationService(IOperationRepository operationRepository, IUserAccountRepository userAccountRepository) {
		this.operationRepository = operationRepository;
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public Operation addOperation(int currentUserId, int beneficiary, double amount, String description) {
		Operation operation = new Operation(currentUserId, beneficiary, new Date(), amount, description);
		UserAccount userAccount1 = new UserAccount(currentUserId, new Date(), -amount);
		UserAccount userAccount2 = new UserAccount(beneficiary, new Date(), amount);
		userAccountRepository.save(userAccount1);
		//int id = userAccount1.getId();
		userAccountRepository.save(userAccount2);
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

	@Override
	public void manageUserAccount(int currentUser, int amount) {
		UserAccount userAccount = new UserAccount(currentUser, new Date(), amount);
		userAccountRepository.save(userAccount);
	}
}