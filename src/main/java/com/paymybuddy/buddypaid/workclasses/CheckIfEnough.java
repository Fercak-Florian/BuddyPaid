package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CheckIfEnough {
	
	public CheckIfEnough(){
	}
	
	public boolean isEnough(double amount, double commission, double credit, double debit) {
		double fullAmount = amount + commission;
		double balance = credit - debit;
		double necessaryAmount = balance - fullAmount;
		if(necessaryAmount < 0) {
			return false;
		}else {
			return true;
		}
	}
}