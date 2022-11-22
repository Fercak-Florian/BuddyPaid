package com.paymybuddy.buddypaid.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "operation")
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "buddy_id")
	private int buddyId;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "description")
	private String description;
	
	public Operation() {
	}
	
	public Operation(int id, int userId, int buddyId, Date date, double amount, String description) {
		this.id = id;
		this.buddyId = buddyId;
		this.userId = userId;
		this.date = date;
		this.amount = amount;
		this.description = description;
	}
	
	public Operation(int userId, int buddyId, Date date, double amount, String description) {
		this.userId = userId;
		this.buddyId = buddyId;
		this.date = date;
		this.amount = amount;
		this.description = description;
	}
}
