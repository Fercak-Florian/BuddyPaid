package com.paymybuddy.buddypaid.workclasses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.repository.IUserRepository;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.service.UserService;

@SpringBootTest
public class CurrentUserTest {
	
	private CurrentUser currentUser;
	private IUserService userService;
	@Autowired
	private IUserRepository userRepository;
	
	@BeforeEach
	public void init() {
		userService = new UserService(userRepository);
		currentUser = new CurrentUser(userService);
	}
	
	@Test
	@WithMockUser("jboyd@email.com")
	public void testGetCurrentUser() {
		/*ARRANGE*/
		/*ACT*/
		User result = currentUser.getCurrentUser();
		/*ASSERT*/
		assertThat(result.getFirstName()).isEqualTo("John");
	}
}