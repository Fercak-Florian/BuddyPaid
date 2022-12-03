package com.paymybuddy.buddypaid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.workclasses.CurrentUser;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperation;
import com.paymybuddy.buddypaid.workclasses.FormAddConnectionTh;
import com.paymybuddy.buddypaid.workclasses.FormComment;
import com.paymybuddy.buddypaid.workclasses.ModifiedUser;
import com.paymybuddy.buddypaid.workclasses.PartialDisplayOperation;
import com.paymybuddy.buddypaid.workclasses.TextArea;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Controller
public class UserController {
	
	private IUserService userService;
	private PartialDisplayOperation partialDisplayOperation;
	private FormComment contactFormComment = new FormComment();
	private FormComment addConnectionFormComment = new FormComment();
	private CurrentUser currentUser;
	
	public UserController(IUserService userService, PartialDisplayOperation partialDisplayOperation, CurrentUser currentUser) {
		this.userService = userService;
		this.partialDisplayOperation = partialDisplayOperation;
		this.currentUser = currentUser;
	}
	
	@GetMapping("/")
	public String redirectToLogin() {
		return "login";
	}
	
	@GetMapping("/transfer")
	public String displayTransferPage(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		User user = currentUser.getCurrentUser();
		List<User> buddies = user.getBuddies();
		List<Operation> operations = user.getOperations();

		/* ALLER CHERCHER LE BUDDY FIRSTNAME POUR CHAQUE OPERATION */
		model.addAttribute("operations", operations);
		model.addAttribute("buddies", buddies);
		List<DisplayedOperation> displayedOperations = new ArrayList<>();
		for (Operation o : operations) {
			if (o.getBuddyId() == 1) { /*BANK USERID IS ALWAYS 1*/
				/*NE PAS AJOUTER DANS LA LISTE*/
			} else {
				int buddyId = o.getBuddyId();
				Optional<User> optUser = userService.getUser(buddyId);
				User u = optUser.get();
				displayedOperations.add(new DisplayedOperation(u.getFirstName(), o.getDescription(), o.getAmount()));
			}
		}
		List<DisplayedOperation> partialDisplayedOperations = partialDisplayOperation.calculateNumberOfOperationsPerPage(displayedOperations, page);
		model.addAttribute("displayedOperations", partialDisplayedOperations);
		return "transfer";
	}

	@GetMapping("/add_connection")
	public String displayAddConnectionPage(Model model) {
		Iterable<User> users = userService.getUsers();
		User user = currentUser.getCurrentUser();
		List<User> buddies = user.getBuddies();
		model.addAttribute("buddies", buddies);
		model.addAttribute("formComment", addConnectionFormComment);
		model.addAttribute("users", users);
		return "add_connection";
	}

	@GetMapping("/home")
	public String displayHomePage() {
		return "home";
	}

	@GetMapping("/profile")
	public String displayProfilePage(Model model) {
		User user = currentUser.getCurrentUser();
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PostMapping("/modifyProfile")
	public String modifyProfile(@ModelAttribute ModifiedUser modifiedUser) {
		System.out.println(modifiedUser.getFirstName());
		User user = currentUser.getCurrentUser();
		System.out.println(user.getId());
		userService.saveUser(user.getId(), user.getLogin(), user.getPassword(), modifiedUser.getFirstName(), modifiedUser.getLastName());
		return "redirect:/profile";
	}
	
	@GetMapping("/contact")
	public String displayContactPage(Model model) {
		model.addAttribute("formComment", contactFormComment);
		return "contact";
	}
	
	@PostMapping("/submitDemand")
	public String submitDemand(@ModelAttribute TextArea textArea) {
		System.out.println("Demande de l'utilisateur : " + textArea.getMessage());
		/*FONCTIONNALITE DE TRAITEMENT D'UNE DEMANDE UTILISATEUR*/
		if(textArea.getMessage().isEmpty()) {
			contactFormComment.setMessage("");
		} else {
			contactFormComment.setMessage("We successfully received your inquiry.\r\n"
					+ "We will process it as soon as possible.");
		}
		return "redirect:/contact";
	}

	@GetMapping("/logoff")
	public String displayLogOff() {
		return "logoff";
	}

	@PostMapping("/addBuddy")
	public ModelAndView addBuddy(@ModelAttribute FormAddConnectionTh formAddConnectionTh) {
		String wantedEmail = "";
		if (formAddConnectionTh.getLogin().isEmpty()) {
			wantedEmail = formAddConnectionTh.getBuddyEmail();
		} else {
			wantedEmail = formAddConnectionTh.getLogin();
		}
		Optional<User> user = userService.findUserByLogin(wantedEmail);
		if (user.isEmpty()) {
			System.out.println("Utilisateur non trouvé");
			addConnectionFormComment.setError(true);
			addConnectionFormComment.setMessage("The email " + wantedEmail + " is unknown in the database");
			return new ModelAndView("redirect:/add_connection");
		} else {
			User authenticatedUser = currentUser.getCurrentUser();
			List<User> buddies = authenticatedUser.getBuddies();
			addConnectionFormComment.setMessage("");
			/* VERIFIER SI L'AMI FAIT DEJA PARTI DE LA LISTE AVANT DE L'AJOUTER */
			List<String> buddiesLogin = new ArrayList<>();
			for (User b : buddies) {
				buddiesLogin.add(b.getLogin());
			}
			if (buddiesLogin.contains(wantedEmail)) {
				log.info("Impossible d'ajouter : " + user.get().getFirstName());
				addConnectionFormComment.setError(true);
				addConnectionFormComment.setMessage(user.get().getFirstName() + " is already one of your buddy");
			} else {
				userService.addBuddy(authenticatedUser.getId(), user.get().getId());
				log.info("Utilisateur ajouté : " + user.get().getFirstName());
				addConnectionFormComment.setError(false);
				addConnectionFormComment.setMessage("You added " + user.get().getFirstName() + " to your buddies");
			}
		}
		return new ModelAndView("redirect:/add_connection");
	}
}
