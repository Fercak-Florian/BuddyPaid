package com.paymybuddy.buddypaid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.buddypaid.service.IOperationService;
import com.paymybuddy.buddypaid.workclasses.CurrentUserId;
import com.paymybuddy.buddypaid.workclasses.Description;
import com.paymybuddy.buddypaid.workclasses.Transaction;

@Controller
public class OperationController {
	
	private IOperationService operationService;
	private CurrentUserId currentUserId;
	
	public OperationController(IOperationService operationService, CurrentUserId currenUserId) {
		this.operationService = operationService;
		this.currentUserId = currenUserId;
	}
	
	Transaction fullTransaction = new Transaction();
	
	@PostMapping("/addOperation")
	public String addOperation(@ModelAttribute Transaction transaction, Model model) {
		/**/
		fullTransaction.setAmount(transaction.getAmount());
		fullTransaction.setBuddyId(transaction.getBuddyId());
		/**/
		model.addAttribute("transaction", transaction);
		return "description";
	}
	
	@PostMapping("/confirmOperation")
	public String confirmOperation(@ModelAttribute Description description) {
		fullTransaction.setDescription(description.getDescription());
		
		/*CALCUL DU SOLDE AVANT DE FAIRE LE VIREMENT*/
		int debit = operationService.getDebit(currentUserId.getId());
		int credit = operationService.getCredit(currentUserId.getId());
		System.out.println("L'utilisateur actuel est : " + currentUserId.getId());
		System.out.println("La somme des débits est : " + debit);
		System.out.println("La sommes des crédits est : " + credit);
		int result = credit - debit;
		System.out.println("Le solde est : " + result);
		/**/
		if(result < 0) {
			System.out.println("Argent insuffisant pour réaliser le virement");
		} else {
			operationService.addOperation(currentUserId.getId(), fullTransaction.getBuddyId(), fullTransaction.getAmount(), fullTransaction.getDescription());
		}
		return "redirect:/";
	}
}
