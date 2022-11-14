package com.paymybuddy.buddypaid;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.buddypaid.model.Buddy;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.UserService;

@Transactional
@SpringBootApplication
public class BuddyPaidApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BuddyPaidApplication.class, args);
	}
}
