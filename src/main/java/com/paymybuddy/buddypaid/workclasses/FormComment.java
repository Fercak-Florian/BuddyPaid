package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FormComment {
	private String message = "";
	
	public FormComment() {
	}
	
	public FormComment(String message) {
		this.message = message;
	}
}
