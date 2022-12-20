package com.paymybuddy.buddypaid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.buddypaid.repository.IUserAccountRepository;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTest {
	
	private IUserAccountService userAccountService;
	
	@Mock
	private IUserAccountRepository userAccountRepository;
	
	@BeforeEach
	public void init() {
		userAccountService = new UserAccountService(userAccountRepository);
	}
	
	@Test
	public void testGetBalance() {
		/*ARRANGE*/
		when(userAccountRepository.sumOfAmount(2)).thenReturn(10.0);
		/*ACT*/
		double result = userAccountService.getBalance(2);
		/*ASSERT*/
		assertThat(result).isEqualTo(10.0);
		verify(userAccountRepository).sumOfAmount(2);
	}
	
	@Test
	public void testGetBalanceThrowsException() {
		/*ARRANGE*/
		when(userAccountRepository.sumOfAmount(3)).thenThrow(new NullPointerException());
		/*ACT*/
		double result = userAccountService.getBalance(3);
		/*ASSERT*/
		assertThat(result).isEqualTo(0);
		verify(userAccountRepository).sumOfAmount(3);
	}
}