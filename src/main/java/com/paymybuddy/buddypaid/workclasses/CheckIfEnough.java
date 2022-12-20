package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CheckIfEnough {
	
	public CheckIfEnough(){
	}
	
	public boolean isEnough(double amount, double commission, double accountAmount) {
		double fullAmount = amount + commission;
		double balance = accountAmount;
		double necessaryAmount = balance - fullAmount;
		if(necessaryAmount < 0) {
			return false;
		}else {
			return true;
		}
	}
}