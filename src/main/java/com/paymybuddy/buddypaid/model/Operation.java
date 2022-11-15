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
	
	@Column(name = "buddy_first_name")
	private String buddyFirstName;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "positive_amount")
	private float positiveAmount;
	
	@Column(name = "negative_amount")
	private float negativeAmount;
	
	@Column(name = "description")
	private String description;
	
	public Operation() {
	}
	
	
	public Operation(int userId, Date date, String type, float positiveAmount, float negativeAmount, String description) {
		this.userId = userId;
		this.date = date;
		this.type = type;
		this.positiveAmount = positiveAmount;
		this.negativeAmount = negativeAmount;
		this.description = description;
	}
	
	public Operation(int id, int userId, Date date, String type, float positiveAmount, float negativeAmount, String description) {
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.type = type;
		this.positiveAmount = positiveAmount;
		this.negativeAmount = negativeAmount;
		this.description = description;
	}
}
