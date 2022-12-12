package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FormComment {
	private String message = "";
	private boolean error;
	
	public FormComment() {
	}
	
	public FormComment(String message) {
		this.message = message;
	}
	
	public boolean isEmpty() {
		return this.message.isEmpty();
	}
	
	public boolean clearMessage() {
		this.message = "";
		return true;
	}
}