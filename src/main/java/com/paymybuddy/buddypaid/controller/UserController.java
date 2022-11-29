package com.paymybuddy.buddypaid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.paymybuddy.buddypaid.workclasses.CurrentUserId;
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
	
	private CurrentUserId currentUserId;
	private IUserService userService;
	private FormComment formComment;
	private PartialDisplayOperation partialDisplayOperation;
	
	public UserController(IUserService userService, CurrentUserId currentUserId, FormComment formComment, PartialDisplayOperation partialDisplayOperation) {
		this.userService = userService;
		this.currentUserId = currentUserId;
		this.formComment = formComment;
		this.partialDisplayOperation = partialDisplayOperation;
	}

	/*public User getCurrentUser() {
		Optional<User> optUser = userService.getUser(currentUserId.getId());
		User user = optUser.get();
		return user;
	}*/
	
	public User getCurrentUser/*ByEmail*/() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		System.out.println(currentUserName);
		Optional<User> optUser = userService.findUserByLogin(currentUserName);
		User user = optUser.get();
		return user;
	}

	@GetMapping("/")
	public String displayTransferPage(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		User user = getCurrentUser();
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
		User user = getCurrentUser();
		List<User> buddies = user.getBuddies();
		model.addAttribute("buddies", buddies);
		model.addAttribute("formComment", formComment);
		model.addAttribute("users", users);
		return "add_connection";
	}

	@GetMapping("/home")
	public String displayHomePage() {
		return "home";
	}

	@GetMapping("/transfer")
	public ModelAndView displayTranferPage() {
		return new ModelAndView("redirect:/");
	}

	@GetMapping("/profile")
	public String displayProfilePage(Model model) {
		User user = getCurrentUser();
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PostMapping("/modifyProfile")
	public String modifyProfile(@ModelAttribute ModifiedUser modifiedUser) {
		System.out.println(modifiedUser.getFirstName());
		User currentUser = getCurrentUser();
		System.out.println(currentUser.getId());
		userService.saveUser(currentUser.getId(), currentUser.getLogin(), currentUser.getPassword(), modifiedUser.getFirstName(), modifiedUser.getLastName());
		return "redirect:/profile";
	}

	@GetMapping("/contact")
	public String displayContactPage(Model model) {
		model.addAttribute("formComment", formComment);
		return "contact";
	}
	
	@PostMapping("/submitDemand")
	public String submitDemand(@ModelAttribute TextArea textArea) {
		System.out.println("Demande de l'utilisateur : " + textArea.getMessage());
		/*FONCTIONNALITE DE TRAITEMENT D'UNE DEMANDE UTILISATEUR*/
		if(textArea.getMessage().isEmpty()) {
			formComment.setMessage("");
		} else {
			formComment.setMessage("We successfully received your inquiry.\r\n"
					+ "We will process it as soon as possible.");
		}
		return "redirect:/contact";
	}

	@GetMapping("/log_off")
	public String displayLogOff() {
		return "log_off";
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
			formComment.setMessage("The email " + wantedEmail + " is unknown in the database");
			return new ModelAndView("redirect:/add_connection");
		} else {
			User currentUser = getCurrentUser();
			List<User> buddies = currentUser.getBuddies();
			formComment.setMessage("");
			/* VERIFIER SI L'AMI FAIT DEJA PARTI DE LA LISTE AVANT DE L'AJOUTER */
			List<String> buddiesLogin = new ArrayList<>();
			for (User b : buddies) {
				buddiesLogin.add(b.getLogin());
			}
			if (buddiesLogin.contains(wantedEmail)) {
				log.info("Impossible d'ajouter : " + user.get().getFirstName());
				formComment.setMessage(user.get().getFirstName() + " is already one of your buddy");
			} else {
				userService.addBuddy(getCurrentUser().getId(), user.get().getId());
				log.info("Utilisateur ajouté : " + user.get().getFirstName());
				formComment.setMessage("You added " + user.get().getFirstName() + " to your buddies");
			}
		}
		return new ModelAndView("redirect:/add_connection");
	}
}
