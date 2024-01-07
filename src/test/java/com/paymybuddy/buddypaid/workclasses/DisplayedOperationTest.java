package com.paymybuddy.buddypaid.workclasses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DisplayedOperationTest {

	@Test
	public void testGetAmountInEuro() {
		/*ARRANGE*/
		DisplayedOperation displayedOperation = new DisplayedOperation("Eric", "Bowling", 10);
		/*ACT*/
		String result = displayedOperation.getAmountInEuro();
		/*ASSERT*/
		assertThat(result).contains("10.0€");
	}
}