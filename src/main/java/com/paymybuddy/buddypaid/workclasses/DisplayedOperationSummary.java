package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DisplayedOperationSummary {
	private String buddyFirstName;
	private String buddyLastName;
	private double amount;
	
	public DisplayedOperationSummary() {
	}
	
	public DisplayedOperationSummary(String buddyFirstName, String buddyLastName, float amount) {
		this.buddyFirstName = buddyFirstName;
		this.buddyLastName = buddyLastName;
		this.amount = amount;
	}
}
