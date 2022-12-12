package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;

public interface IUserService {
	public Iterable<User> getUsers();

	public Optional<User> getUser(Integer id);

	public UserBuddy addBuddy(int userId, int buddyId);

	public Optional<User> findUserByLogin(String login);

	public User saveUser(int currentUserId, String login, String password, String firstName, String lastName);
	
	public User registerNewUserAccount(User userDto);
}
