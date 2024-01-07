package com.paymybuddy.buddypaid.service;

import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.repository.IUserAccountRepository;

@Service
public class UserAccountService implements IUserAccountService{

	private IUserAccountRepository userAccountRepository;

	public UserAccountService(IUserAccountRepository userAccountRepository) {
		this.userAccountRepository = userAccountRepository;
	}

	public double getBalance(int userId) {
		double value = 0;
		try {
			value = userAccountRepository.sumOfAmount(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}
}
