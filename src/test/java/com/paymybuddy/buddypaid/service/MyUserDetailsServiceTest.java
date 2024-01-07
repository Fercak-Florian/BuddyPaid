package com.paymybuddy.buddypaid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.repository.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

	private MyUserDetailsService userDetailsService;

	@Mock
	private IUserRepository userRepository;

	@BeforeEach
	public void init() {
		userDetailsService = new MyUserDetailsService(userRepository);
	}

	@Test
	public void testLoadUserByUserNameSucceed() {
		/* ARRANGE */
		User user = new User();
		user.setFirstName("John");
		user.setLogin("jboyd@email.com");
		user.setPassword("jboyd");
		Optional<User> optUser = Optional.of(user);
		/* ACT */
		when(userRepository.findByLogin("jboyd@email.com")).thenReturn(optUser);
		UserDetails result = userDetailsService.loadUserByUsername("jboyd@email.com");
		/* ASSERT */
		assertThat(result.getUsername()).isEqualTo("jboyd@email.com");
		verify(userRepository).findByLogin("jboyd@email.com");
	}
}