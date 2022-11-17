package com.paymybuddy.buddypaid.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.buddypaid.model.User;
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
	}
}
