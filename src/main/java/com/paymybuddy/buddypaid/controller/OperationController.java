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
import com.paymybuddy.buddypaid.service.IUserAccountService;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.service.UserAccountService;
import com.paymybuddy.buddypaid.workclasses.Amount;
import com.paymybuddy.buddypaid.workclasses.CheckIfEnough;
import com.paymybuddy.buddypaid.workclasses.CurrentUser;
import com.paymybuddy.buddypaid.workclasses.Description;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperationSummary;
import com.paymybuddy.buddypaid.workclasses.FormComment;
import com.paymybuddy.buddypaid.workclasses.LevyPercentage;
import com.paymybuddy.buddypaid.workclasses.Transaction;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class OperationController {
	
	private IOperationService operationService;
	private IUserAccountService userAccountService;
	private IUserService userService;
	private CurrentUser currentUser;
	private DisplayedOperationSummary displayedOperationSummary;
	private LevyPercentage levyPercentage;
	private CheckIfEnough checkIfEnough;
	private FormComment operationFormComment = new FormComment();
	
	public OperationController(IOperationService operationService, IUserAccountService userAccountService, CurrentUser currentUser, IUserService userService , DisplayedOperationSummary displayedOperationSummary, LevyPercentage levyPercentage, CheckIfEnough checkIfEnough) {
		this.operationService = operationService;
		this.userAccountService = userAccountService;
		this.userService = userService;
		this.currentUser = currentUser;
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
		log.info("Display operation summurary page");
		return "description";
	}
	
	@PostMapping("/confirmOperation")
	public ModelAndView confirmOperation(@ModelAttribute Description description) {
		fullTransaction.setDescription(description.getDescription());
		double amount = fullTransaction.getAmount();
		double commission = (amount * levyPercentage.getPercentage()) / 100;
		double accountAmout = userAccountService.getBalance(currentUser.getCurrentUser().getId());
		boolean isEnought = checkIfEnough.isEnough(amount, commission, accountAmout);
		if(isEnought) {
			/*UTILISATEUR, BENEFICIAIRE, MONTANT, DESCRIPTION*/
			operationService.addOperation(currentUser.getCurrentUser().getId(), fullTransaction.getBuddyId(), fullTransaction.getAmount(), fullTransaction.getDescription());
			operationService.addOperation(currentUser.getCurrentUser().getId(), 1, commission, "Money To App"); /*BANK USERID IS 1*/
			operationFormComment.setError(false);
			operationFormComment.setMessage("Transfer completed successfully");
			log.info("Transfer completed successfully");
			return new ModelAndView("redirect:/transfer_result");
		} else {
			/*MESSAGE SUR LA PAGE : SOLDE INSUFFISANT POUR REALISER LE VIREMENT */
			operationFormComment.setError(true);
			operationFormComment.setMessage("Insufficient money to make the transfer");
			log.error("Insufficient money to make the transfer");
			return new ModelAndView("redirect:/transfer_result");
		}
	}
	
	@GetMapping("/transfer_result")
	public String displayTransferResult(Model model) {
		model.addAttribute("formComment", operationFormComment);
		log.info("Display transfer result page");
		return "transfer_result";
	}
	
	@GetMapping("/addmoney")
	public String displayManageUserAccountPage() {
		return "add_money";
	}
	
	@PostMapping("/addmoney")
	public String addMoneytoUserAccount(@ModelAttribute Amount amount) {
		operationService.manageUserAccount(currentUser.getCurrentUser().getId(), amount.getAmount());
		return "add_money";
	}
	
	@GetMapping("/recovermoney")
	public String displayRecoverMoneyPage(Model model) {
		model.addAttribute("formComment", operationFormComment);
		return "recover_money";
	}
	
	@PostMapping("/recovermoney")
	public String recoverMoneyFromUserAccount(@ModelAttribute Amount amount) {
		System.out.println(amount.getAmount());
		double balance = userAccountService.getBalance(currentUser.getCurrentUser().getId());
		if(amount.getAmount() > balance) {
			operationFormComment.setError(true);
			operationFormComment.setMessage("Not enough money");
			return "redirect:/recovermoney";
		}else {
			operationService.manageUserAccount(currentUser.getCurrentUser().getId(), -amount.getAmount());
			operationFormComment.setError(false);
			operationFormComment.setMessage("Your transfer has been done");
			return "redirect:/recovermoney";
		}
	}
}