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
	
	public Iterable<User> getUsers(){
		return userRepository.findAll();
	}

	public Optional<User> getUser(Integer id){
		return userRepository.findById(id);
	}
	
	public Optional<User> getUserBuddies(Integer id){
		return userRepository.findById(id);
	}
	
	@Override
	public void addBuddy(int userId, int buddyId) {
		UserBuddy userBuddy = new UserBuddy(userId, buddyId);
		userRepository.save(userBuddy);
	}

	/*@Override
	public Optional<User> getUser(String firstName, String lastName) {
		return userRepository.findByFirstNameAndLastName(firstName,lastName);
	}*/

	@Override
	public Optional<User> findUser(String login) {
		return userRepository.findByLogin(login);
	}
}
