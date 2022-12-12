package com.paymybuddy.buddypaid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.buddypaid.service.UserService;

@SpringBootApplication
public class BuddyPaidApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BuddyPaidApplication.class, args);
	}
}