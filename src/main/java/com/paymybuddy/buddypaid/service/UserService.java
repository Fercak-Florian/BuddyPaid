package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;
import com.paymybuddy.buddypaid.repository.IUserRepository;

@Service
public class UserService implements IUserService{
	
	private IUserRepository userRepository;
	
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Iterable<User> getUsers(){
		return userRepository.findAll();
	}
	
	@Override
	public Optional<User> getUser(Integer id){
		return userRepository.findById(id);
	}
	
	@Override
	public UserBuddy addBuddy(int userId, int buddyId) {
		UserBuddy userBuddy = new UserBuddy(userId, buddyId);
		return userRepository.save(userBuddy);
	}

	@Override
	public Optional<User> findUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public User saveUser(int currentUserId, String login, String password, String firstName,
			String lastName) {
		User user = new User(currentUserId, login, password, firstName, lastName);
		return userRepository.save(user);
	}

	
}
