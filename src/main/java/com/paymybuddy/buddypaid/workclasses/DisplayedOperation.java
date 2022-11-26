package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DisplayedOperation {
	private String buddyFirstName;
	private String description;
	private double amount;
	
	public DisplayedOperation() {
	}
	
	public DisplayedOperation(String buddyFirstName, String description, double amount) {
		this.buddyFirstName = buddyFirstName;
		this.description = description;
		this.amount = amount;
	}
	
	public String getAmountInEuro() {
		return amount + "€";
	}
}
