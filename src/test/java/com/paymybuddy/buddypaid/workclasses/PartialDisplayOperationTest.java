package com.paymybuddy.buddypaid.workclasses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PartialDisplayOperationTest {
	
	private PartialDisplayOperation partialDisplayOperation;
	
	@BeforeEach
	public void init() {
		partialDisplayOperation = new PartialDisplayOperation();
	}
	
	@Test
	public void testCalculateNumberOfOperationsPerPage() {
		/*ARRANGE*/
		int page = 1;
		List<DisplayedOperation> displayedOperation = new ArrayList<>();
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation("John", "Bowling", 10));
		displayedOperation.add(new DisplayedOperation());
		/*ACT*/
		List<DisplayedOperation> result = partialDisplayOperation.calculateNumberOfOperationsPerPage(displayedOperation, page);
		/*ASSERT*/
		assertThat(result.size()).isBetween(0, 3);
		assertThat(result.get(2).getAmount()).isEqualTo(10);
	}
	
	@Test
	public void testCalculateNumberOfOperationsPerPageWithAWrongStart() {
		/*ARRANGE*/
		int page = 3;
		List<DisplayedOperation> displayedOperation = new ArrayList<>();
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation("John", "Bowling", 10));
		displayedOperation.add(new DisplayedOperation());
		/*ACT*/
		List<DisplayedOperation> result = partialDisplayOperation.calculateNumberOfOperationsPerPage(displayedOperation, page);
		/*ASSERT*/
		assertThat(result.size()).isEqualTo(0);
	}
	
	@Test
	public void testCalculateNumberOfOperationsPerPageWithPageIsEqualToTwo() {
		/*ARRANGE*/
		int page = 2;
		List<DisplayedOperation> displayedOperation = new ArrayList<>();
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation());
		displayedOperation.add(new DisplayedOperation("John", "Bowling", 10));
		displayedOperation.add(new DisplayedOperation());
		/*ACT*/
		List<DisplayedOperation> result = partialDisplayOperation.calculateNumberOfOperationsPerPage(displayedOperation, page);
		/*ASSERT*/
		assertThat(result.size()).isEqualTo(1);
	}
}