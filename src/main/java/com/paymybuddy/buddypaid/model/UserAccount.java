package com.paymybuddy.buddypaid.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "amount")
	private double amount;
	
	public UserAccount() {
		
	}
	
	public UserAccount(int userId, Date date, double amount) {
		this.userId = userId;
		this.date = date;
		this.amount = amount;
	}
}
