package com.paymybuddy.buddypaid.workclasses;

import lombok.Data;

@Data
public class Amount {
	private int amount;
	
	public Amount() {
	}
	
	public Amount(int amount) {
		this.amount = amount;
	}
}
