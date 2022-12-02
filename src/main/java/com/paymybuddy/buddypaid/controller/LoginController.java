package com.paymybuddy.buddypaid.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.buddypaid.workclasses.FormComment;

@Controller
public class LoginController {
	
	private FormComment errorLoginFormComment = new FormComment();

	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", defaultValue = "false") boolean error) {
		System.out.println("La valeur de error est : " + error);
		errorLoginFormComment.setError(error);
		errorLoginFormComment.setMessage("bad credentials");
		model.addAttribute("formComment", errorLoginFormComment);
		return "login";
	}
}
