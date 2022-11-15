package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.workclasses.Transaction;

public interface IUserService {
	public Iterable<User> getUsers();
	public Optional<User> getUser(Integer id);
	public Optional<User> getUserBuddies(Integer id);
	public void addBuddy(int userId, int buddyId);
	public Optional<User> getUser(String firstName, String lastName);
	public Optional<User> findUser(String login);
}
