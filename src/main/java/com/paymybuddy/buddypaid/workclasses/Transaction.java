package com.paymybuddy.buddypaid.workclasses;


import lombok.Data;

@Data
public class Transaction {
	private int buddyId;
	private int amount;
	private String description = "";
}
