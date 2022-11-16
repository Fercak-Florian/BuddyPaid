package com.paymybuddy.buddypaid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping("/confirmOperation")
	public String confirmOperation(@ModelAttribute Transaction transaction, Model model) {
		System.out.println("Je suis dans la methode confirmOperation");
		System.out.println("Id de l'utilisateur connecté : " + currentUserId.getId());
		System.out.println("Montant transmis : " + transaction.getAmount());
		System.out.println("Id du beneficiaire : " + transaction.getBuddyId());
		/**/
		fullTransaction.setAmount(transaction.getAmount());
		fullTransaction.setBuddyId(transaction.getBuddyId());
		/**/
		model.addAttribute("transaction", transaction);
		return "description";
	}
	
	
	@PostMapping("/saveOperation")
	public String saveOperation(@ModelAttribute Description description) {
		System.out.println("Je suis dans la methode saveOperation");
		System.out.println("Id de l'utilisateur connecté : " + currentUserId.getId());
		System.out.println("Montant transmis : " + fullTransaction.getAmount());
		System.out.println("Id du beneficiaire : " + fullTransaction.getBuddyId());
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
