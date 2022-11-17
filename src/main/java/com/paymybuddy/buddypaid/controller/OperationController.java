package com.paymybuddy.buddypaid.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IOperationService;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.workclasses.CurrentUserId;
import com.paymybuddy.buddypaid.workclasses.Description;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperationSummary;
import com.paymybuddy.buddypaid.workclasses.LevyPercentage;
import com.paymybuddy.buddypaid.workclasses.Transaction;

@Controller
public class OperationController {
	
	private IOperationService operationService;
	private IUserService userService;
	private CurrentUserId currentUserId;
	private DisplayedOperationSummary displayedOperationSummary;
	private LevyPercentage levyPercentage;
	
	public OperationController(IOperationService operationService, CurrentUserId currenUserId, IUserService userService , DisplayedOperationSummary displayedOperationSummary, LevyPercentage levyPercentage) {
		this.operationService = operationService;
		this.userService = userService;
		this.currentUserId = currenUserId;
		this.displayedOperationSummary = displayedOperationSummary;
		this.levyPercentage = levyPercentage;
	}
	
	Transaction fullTransaction = new Transaction();
	
	@PostMapping("/addOperation")
	public String addOperation(@ModelAttribute Transaction transaction, Model model) {
		fullTransaction.setAmount(transaction.getAmount());
		fullTransaction.setBuddyId(transaction.getBuddyId());
		Optional<User> optUser = userService.getUser(transaction.getBuddyId());
		User user = optUser.get();
		displayedOperationSummary.setBuddyFirstName(user.getFirstName());
		displayedOperationSummary.setBuddyLastName(user.getLastName());
		displayedOperationSummary.setAmount(transaction.getAmount());
		model.addAttribute("displayedOperationSummary", displayedOperationSummary);
		return "description";
	}
	
	@PostMapping("/confirmOperation")
	public String confirmOperation(@ModelAttribute Description description) {
		fullTransaction.setDescription(description.getDescription());
		double amount = fullTransaction.getAmount();
		/*PRELEVEMENT DE 0.5% SUR CHAQUE TRANSACTION*/
		
		/*CALCUL DE 0.5%*/
		double commission = (amount * levyPercentage.getPercentage()) / 100;
		double fullAmount = amount + commission;
		
		double debit = operationService.getDebit(currentUserId.getId());
		double credit = operationService.getCredit(currentUserId.getId());
		System.out.println("L'utilisateur actuel est : " + currentUserId.getId());
		System.out.println("La somme des débits est : " + debit);
		System.out.println("La sommes des crédits est : " + credit);
		/*CALCUL DU SOLDE AVANT DE FAIRE LE VIREMENT*/
		double balance = credit - debit;
		System.out.println("Le solde est : " + balance);
		double necessaryAmount = balance - fullAmount;
		if(necessaryAmount < 0) {
			System.out.println("Un solde de " + fullAmount + "€" + " est nécessaire pour realiser le virement");
			System.out.println("Le solde du compte est actuellement de : " + balance + "€" + " Argent insuffisant pour réaliser le virement");
		} else {
			/*UTILISATEUR, BENEFICIAIRE, MONTANT, DESCRIPTION*/
			operationService.addOperation(currentUserId.getId(), fullTransaction.getBuddyId(), fullTransaction.getAmount(), fullTransaction.getDescription());
			operationService.addOperation(currentUserId.getId(), 1, commission, "Money To App"); /*BANK USERID IS 1*/
		}
		return "redirect:/";
	}
}
