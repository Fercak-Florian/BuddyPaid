package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;
import com.paymybuddy.buddypaid.workclasses.Transaction;

public interface IUserService {
	public Iterable<User> getUsers();
	public Optional<User> getUser(Integer id);
	public UserBuddy addBuddy(int userId, int buddyId);
	public Optional<User> findUserByLogin(String login);
}
