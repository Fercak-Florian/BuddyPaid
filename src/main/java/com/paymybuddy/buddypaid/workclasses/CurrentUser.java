package com.paymybuddy.buddypaid.workclasses;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IUserService;

@Component
public class CurrentUser {

	IUserService userService;

	public CurrentUser(IUserService userService) {
		this.userService = userService;
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		System.out.println(currentUserName);
		Optional<User> optUser = userService.findUserByLogin(currentUserName);
		User user = optUser.get();
		return user;
	}
}
