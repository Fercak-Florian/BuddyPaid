package com.paymybuddy.buddypaid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IUserService;

@Controller
public class RegistrationController {
	
	private IUserService userService;

	public RegistrationController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute User user) {
		userService.registerNewUserAccount(user);
		return "login";
	}
}
