package com.paymybuddy.buddypaid.workclasses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckIfEnoughTest {
	
	private CheckIfEnough checkIfEnough;
	
	@BeforeEach
	public void init() {
		checkIfEnough = new CheckIfEnough();
	}
	
	@Test
	public void testIsEnoughWhenCreditIsSufficient() {
		/*ARRANGE*/
		/*ACT*/
		boolean result = checkIfEnough.isEnough(10, 0.05, 30, 10);
		/*ASSERT*/
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIsEnoughWhenCreditIsNotSufficient() {
		/*ARRANGE*/
		/*ACT*/
		boolean result = checkIfEnough.isEnough(10, 0.05, 20, 10);
		/*ASSERT*/
		assertThat(result).isFalse();
	}
}