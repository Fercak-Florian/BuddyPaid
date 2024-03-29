package com.paymybuddy.buddypaid.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.buddypaid.workclasses.FormComment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	private FormComment errorLoginFormComment = new FormComment();

	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", defaultValue = "false") boolean error) {
		errorLoginFormComment.setError(error);
		errorLoginFormComment.setMessage("Bad credentials");
		model.addAttribute("formComment", errorLoginFormComment);
		log.info("Display login page");
		return "login";
	}
}