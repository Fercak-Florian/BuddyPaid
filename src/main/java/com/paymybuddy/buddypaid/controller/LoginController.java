package com.paymybuddy.buddypaid.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String logIn() {
		return "login";
	}
}
