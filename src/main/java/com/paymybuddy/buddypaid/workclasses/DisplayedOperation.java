package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DisplayedOperation {
	private String buddyFirstName;
	private String description;
	private float amount;
	
	public DisplayedOperation() {
	}
	
	public DisplayedOperation(String buddyFirstName, String description, float amount) {
		this.buddyFirstName = buddyFirstName;
		this.description = description;
		this.amount = amount;
	}
}
