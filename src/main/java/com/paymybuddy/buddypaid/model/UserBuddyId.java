package com.paymybuddy.buddypaid.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserBuddyId implements Serializable{

	private int userId;
	private int buddyId;

	public UserBuddyId(int userId, int buddyId) {
		this.userId = userId;
		this.buddyId = buddyId;
	}
}
