package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ModifiedUser {
	private String firstName;
	private String lastName;
}
