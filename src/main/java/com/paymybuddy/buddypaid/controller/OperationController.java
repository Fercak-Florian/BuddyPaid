package com.paymybuddy.buddypaid.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IOperationService;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.workclasses.CheckIfEnough;
import com.paymybuddy.buddypaid.workclasses.CurrentUserId;
import com.paymybuddy.buddypaid.workclasses.Description;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperationSummary;
import com.paymybuddy.buddypaid.workclasses.FormComment;
import com.paymybuddy.buddypaid.workclasses.LevyPercentage;
import com.paymybuddy.buddypaid.workclasses.Transaction;

@Controller
public class OperationController {
	
	private IOperationService operationService;
	private IUserService userService;
	private CurrentUserId currentUserId;
	private DisplayedOperationSummary displayedOperationSummary;
	private LevyPercentage levyPercentage;
	private CheckIfEnough checkIfEnough;
	private FormComment formComment;
	
	public OperationController(IOperationService operationService, CurrentUserId currenUserId, IUserService userService , DisplayedOperationSummary displayedOperationSummary, LevyPercentage levyPercentage, FormComment formComment, CheckIfEnough checkIfEnough) {
		this.operationService = operationService;
		this.userService = userService;
		this.currentUserId = currenUserId;
		this.displayedOperationSummary = displayedOperationSummary;
		this.levyPercentage = levyPercentage;
		this.formComment = formComment;
		this.checkIfEnough = checkIfEnough;
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
	public ModelAndView confirmOperation(@ModelAttribute Description description) {
		fullTransaction.setDescription(description.getDescription());
		double amount = fullTransaction.getAmount();
		double commission = (amount * levyPercentage.getPercentage()) / 100;
		double debit = operationService.getDebit(currentUserId.getId());
		double credit = operationService.getCredit(currentUserId.getId());
		boolean isEnought = checkIfEnough.isEnough(amount, commission, credit, debit);
		if(isEnought) {
			/*UTILISATEUR, BENEFICIAIRE, MONTANT, DESCRIPTION*/
			operationService.addOperation(currentUserId.getId(), fullTransaction.getBuddyId(), fullTransaction.getAmount(), fullTransaction.getDescription());
			operationService.addOperation(currentUserId.getId(), 1, commission, "Money To App"); /*BANK USERID IS 1*/
			return new ModelAndView("redirect:/transfer");
		} else {
			/*MESSAGE SUR LA PAGE : SOLDE INSUFFISANT POUR REALISER LE VIREMENT */
		}
		return new ModelAndView("redirect:/");
	}
}
