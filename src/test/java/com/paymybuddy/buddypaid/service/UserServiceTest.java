package com.paymybuddy.buddypaid.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;
import com.paymybuddy.buddypaid.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private IUserService userService;
	
	@Mock
	private IUserRepository userRepository;
	
	@BeforeEach
	public void init() {
		userService = new UserService(userRepository);
	}
	
	@Test
	public void testGetUsers() {
		/*ARRANGE*/
		List<User> users = new ArrayList<>();
		users.add(new User("jboyd@email.com", "jboyd", "John", "Boyd", new ArrayList<>(), new ArrayList<>()));
		when(userRepository.findAll()).thenReturn(users);
		/*ACT*/
		Iterable<User> iterableUsers = userService.getUsers();
		Iterator<User> iteratorUsers = iterableUsers.iterator();
		String result = iteratorUsers.next().getFirstName();
		/*ASSERT*/
		assertThat(result).isEqualTo("John");
		verify(userRepository).findAll();
	}
	
	@Test
	public void testGetUser() {
		/*ARRANGE*/
		int id = 0;
		User user = new User("jboyd@email.com", "jboyd", "John", "Boyd", new ArrayList<>(), new ArrayList<>());
		Optional<User> optUser = Optional.of(user);
		when(userRepository.findById(id)).thenReturn(optUser);
		/*ACT*/
		Optional<User> result = userService.getUser(id);
		/*ASSERT*/
		assertThat(result.get().getFirstName()).isEqualTo("John");
		verify(userRepository).findById(id);
	}
	
	@Test
	public void testAddBuddy() {
		/*ARRANGE*/
		int userId = 2;
		int buddyId = 3;
		UserBuddy userBuddy = new UserBuddy(2,3);
		/*ACT*/
		when(userRepository.save(userBuddy)).thenReturn(userBuddy);
		UserBuddy result = userService.addBuddy(userId, buddyId);
		/*ASSERT*/
		assertThat(result.getUserId()).isEqualTo(2);
		assertThat(result.getBuddyId()).isEqualTo(3);
		verify(userRepository).save(userBuddy);
	}
	
	@Test
	public void testFindUserByLogin() {
		/*ARRANGE*/
		String login = "jboyd@email.com";
		User user = new User("jboyd@email.com", "jboyd", "John", "Boyd", new ArrayList<>(), new ArrayList<>());
		Optional<User> optUser = Optional.of(user);
		when(userRepository.findByLogin(login)).thenReturn(optUser);
		/*ACT*/
		Optional<User> result = userService.findUserByLogin(login);
		/*ASSERT*/
		assertThat(result.get().getFirstName()).isEqualTo("John");
		verify(userRepository).findByLogin(login);
	}
}
