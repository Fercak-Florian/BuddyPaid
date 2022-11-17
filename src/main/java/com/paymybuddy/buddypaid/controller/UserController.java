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
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.workclasses.CurrentUserId;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperation;
import com.paymybuddy.buddypaid.workclasses.FormAddConnectionTh;
import com.paymybuddy.buddypaid.workclasses.FormComment;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Controller
public class UserController {


	private CurrentUserId currentUserId;
	private IUserService userService;
	private FormComment formComment;

	public UserController(IUserService userService, CurrentUserId currentUserId, FormComment formComment) {
		this.userService = userService;
		this.currentUserId = currentUserId;
		this.formComment = formComment;
	}

	public User getCurrentUser() {
		Optional<User> optUser = userService.getUser(currentUserId.getId());
		User user = optUser.get();
		return user;
	}

	@GetMapping("/")
	public String displayTransferPage(Model model) {
		User user = getCurrentUser();
		List<User> buddies = user.getBuddies();
		List<Operation> operations = user.getOperations();

		/* ALLER CHERCHER LE BUDDY FIRSTNAME POUR CHAQUE OPERATION */
		model.addAttribute("operations", operations);
		model.addAttribute("buddies", buddies);
		List<DisplayedOperation> displayedOperations = new ArrayList<>();
		for (Operation o : operations) {
			if (o.getBuddyId() == 1) { /*BANK USERID IS 1*/
				/*NE PAS AJOUTER DANS LA LISTE*/
			} else {
				int buddyId = o.getBuddyId();
				Optional<User> optUser = userService.getUser(buddyId);
				User u = optUser.get();
				displayedOperations.add(new DisplayedOperation(u.getFirstName(), o.getDescription(), o.getAmount()));
				model.addAttribute("displayedOperations", displayedOperations);
			}
		}
		return "transfer";
	}

	@GetMapping("/add_connection.html")
	public String displayAddConnectionPage(Model model) {
		Iterable<User> users = userService.getUsers();
		User user = getCurrentUser();
		List<User> buddies = user.getBuddies();
		model.addAttribute("buddies", buddies);
		model.addAttribute("formComment", formComment);
		model.addAttribute("users", users);
		return "add_connection";
	}

	@GetMapping("/home.html")
	public String displayHomePage() {
		return "home";
	}

	@GetMapping("/transfer.html")
	public ModelAndView displayTranferPage() {
		return new ModelAndView("redirect:/");
	}

	@GetMapping("/profile.html")
	public String displayProfilePage() {
		return "profile";
	}

	@GetMapping("/contact.html")
	public String displayContactPage() {
		return "contact";
	}

	@GetMapping("/log_off.html")
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
		Optional<User> user = userService.findUser(wantedEmail);
		if (user.isEmpty()) {
			System.out.println("Utilisateur non trouvé");
			formComment.setMessage("l'email " + wantedEmail + " n'est pas connu dans la base de données");
			return new ModelAndView("redirect:/add_connection.html");
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
				formComment.setMessage(user.get().getFirstName() + " fait déjà parti de vos amis");
			} else {
				userService.addBuddy(currentUserId.getId(), user.get().getId());
				log.info("Utilisateur ajouté : " + user.get().getFirstName());
				formComment.setMessage("Vous avez ajouté " + user.get().getFirstName() + " à vos amis");
			}
		}
		return new ModelAndView("redirect:/add_connection.html");
	}
}
