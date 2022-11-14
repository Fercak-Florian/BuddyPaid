package com.paymybuddy.buddypaid.workclasses;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FirstNameAndLastName {
	private String firstName;
	private String lastName;
}
