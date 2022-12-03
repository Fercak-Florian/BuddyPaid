package com.paymybuddy.buddypaid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
	
	//@Mock
	//private MyUserPrincipal myUserPrincipal;

	@BeforeEach
	public void init() {
		userDetailsService = new MyUserDetailsService(userRepository);
	}

	@Disabled
	@Test
	public void testLoadUserByUserNameSucceed() {
		/*ARRANGE*/
		String username = "jboyd@email.com";
		User user = new User();
		user.setLogin("jboyd@email.com");
		MyUserPrincipal principal = new MyUserPrincipal(user);
		//UserDetails userDetails = new MyUserPrincipal(user);
		/*ACT*/
		when(userRepository.findByLogin(username).get()).thenReturn(user);
		when(new MyUserPrincipal(user)).thenReturn(principal);
		UserDetails result = userDetailsService.loadUserByUsername(username);
		/*ASSERT*/
		assertThat(result.getUsername()).isEqualTo("jboyd@email.com");
	}
}
