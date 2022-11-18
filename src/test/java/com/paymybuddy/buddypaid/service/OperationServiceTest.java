package com.paymybuddy.buddypaid.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.repository.IOperationRepository;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {

	private IOperationService operationService;
	
	@Mock
	IOperationRepository operationRepository;
	
	@BeforeEach
	public void init() {
		operationService = new OperationService(operationRepository);
	}
	
	@Disabled
	@Test
	public void testAddOperation() {
		/*ARRANGE*/
		int currentUserId = 5;
		int beneficiary = 6;
		double amount = 50;
		String description = "bowling";
		Operation operation1 = new Operation(2, 3, new Date(), "versement", 10, "bowling");
		/*Operation operation2 = new Operation(3, 4, new Date(), "versement", 20, "restaurant");*/
		when(operationRepository.save(new Operation())).thenReturn(operation1);
		
		/*ACT*/
		/*CURRENTUSERID, BENEFICIARY, AMOUNT, DESCRIPTION*/
		Operation result = operationService.addOperation(currentUserId, beneficiary, amount, description);

		/*ASSERT*/
		assertThat(result.getAmount()).isEqualTo(10);
		verify(operationRepository).save(operation1);
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
