package com.paymybuddy.buddypaid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.repository.IOperationRepository;
import com.paymybuddy.buddypaid.repository.IUserAccountRepository;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

	private IOperationService operationService;
	
	@Mock
	IOperationRepository operationRepository;
	
	@Mock
	IUserAccountRepository userAccountRepository;
	
	@Captor
	ArgumentCaptor<Operation> operationCaptor;
	
	@BeforeEach
	public void init() {
		operationService = new OperationService(operationRepository, userAccountRepository);
	}
	
	@Test
	public void testAddOperation() {
		
		/*ARRANGE*/
		int currentUserId = 5;
		int beneficiary = 6;
		double amount = 50;
		String description = "bowling";
		Operation operation1 = new Operation(currentUserId, beneficiary, new Date(), amount, description);
		when(operationRepository.save(any(Operation.class))).thenReturn(operation1);
		
		/*ACT*/
		/*CURRENTUSERID, BENEFICIARY, AMOUNT, DESCRIPTION*/
		Operation result = operationService.addOperation(currentUserId, beneficiary, amount, description);

		/*ASSERT*/
		assertThat(result.getAmount()).isEqualTo(amount);
		verify(operationRepository).save(operationCaptor.capture());
		assertThat(operationCaptor.getValue())
		.extracting("userId", "buddyId", "amount", "description")
		.containsExactly(currentUserId, beneficiary, amount, description);
	}
	
	@Test
	public void testGetDebit() {
		/*ARRANGE*/
		int userId = 2;
		when(operationRepository.sumOfDebit(userId)).thenReturn(10);
		/*ACT*/
		int result = operationService.getDebit(userId);
		/*ASSERT*/
		assertThat(result).isEqualTo(10);
		verify(operationRepository).sumOfDebit(userId);
	}
	
	@Test
	public void testGetCredit() {
		/*ARRANGE*/
		int userId = 2;
		when(operationRepository.sumOfCredit(userId)).thenReturn(10);
		/*ACT*/
		int result = operationService.getCredit(userId);
		/*ASSERT*/
		assertThat(result).isEqualTo(10);
		verify(operationRepository).sumOfCredit(userId);
	}
}
