package com.paymybuddy.buddypaid.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	private FormComment operationFormComment = new FormComment();
	
	public OperationController(IOperationService operationService, CurrentUserId currenUserId, IUserService userService , DisplayedOperationSummary displayedOperationSummary, LevyPercentage levyPercentage, CheckIfEnough checkIfEnough) {
		this.operationService = operationService;
		this.userService = userService;
		this.currentUserId = currenUserId;
		this.displayedOperationSummary = displayedOperationSummary;
		this.levyPercentage = levyPercentage;
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
			operationFormComment.setError(false);
			operationFormComment.setMessage("Transfer completed successfully");
			return new ModelAndView("redirect:/transfer_result");
		} else {
			/*MESSAGE SUR LA PAGE : SOLDE INSUFFISANT POUR REALISER LE VIREMENT */
			operationFormComment.setError(true);
			operationFormComment.setMessage("Insufficient money to make the transfer");
			return new ModelAndView("redirect:/transfer_result");
		}
	}
	
	@GetMapping("/transfer_result")
	public String displayTransferResult(Model model) {
		model.addAttribute("formComment", operationFormComment);
		return "transfer_result";
	}
}
