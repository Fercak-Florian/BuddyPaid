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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.buddypaid.model.Buddy;
import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;
import com.paymybuddy.buddypaid.service.BuddyService;
import com.paymybuddy.buddypaid.service.OperationService;
import com.paymybuddy.buddypaid.service.UserService;
import com.paymybuddy.buddypaid.workclasses.FirstNameAndLastName;
import com.paymybuddy.buddypaid.workclasses.FormAddConnectionTh;
import com.paymybuddy.buddypaid.workclasses.FormComment;
import com.paymybuddy.buddypaid.workclasses.Transaction;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Controller
public class UserController {

	private int currentUserId = 1;
	private int beneficiary = 6;
	private FormComment formComment = new FormComment();
	private float amount = 10;

	private UserService userService;
	private BuddyService buddyService;
	private OperationService operationService;

	public UserController(UserService userService, BuddyService buddyService, OperationService operationService) {
		this.userService = userService;
		this.buddyService = buddyService;
		this.operationService = operationService;
	}
	
	public User getCurrentUser(){
		Optional<User> optUser = userService.getUser(currentUserId);
		User user = optUser.get();
		return user;
	}

	/*
	 * @GetMapping("/") public String getUsers(Model model) {
	 * log.info("Récupération des utilisateurs"); Iterable<User> users =
	 * userService.getUsers(); for (User u : users) {
	 * System.out.println(u.getFirstName()); } model.addAttribute("users", users);
	 * return "index"; }
	 */

	/*
	 * @GetMapping("/") public String getUser(Model model) {
	 * log.info("Récupération d'un utilisateur"); Optional<User> optUser =
	 * userService.getUser(currentUserId); User userId1 = optUser.get(); List<Buddy>
	 * buddies = userId1.getBuddies(); /*for (Buddy b : buddies) {
	 * System.out.println(b.getFirstName()); } List<Operation> operations =
	 * userId1.getOperations(); model.addAttribute("operations", operations);
	 * model.addAttribute("buddies", buddies); return "transfer"; }
	 */

	/*
	 * @GetMapping("/") public String getUserBuddies(Model model) { Iterable<Buddy>
	 * buddies = buddyService.getBuddiesByUserId(currentUserId); for(Buddy b :
	 * buddies) { System.out.println(b.getFirstName() + " " + b.getLastName() + " "
	 * + b.getUser().getFirstName()); } model.addAttribute("buddies", buddies);
	 * return "transfer"; }
	 */

	@GetMapping("/")
	public String getUserBuddies(Model model) {
		User user = getCurrentUser();
		List<User> buddies = user.getBuddies();
		List<Operation> operations = user.getOperations();
		model.addAttribute("operations", operations);
		model.addAttribute("buddies", buddies);
		return "transfer";
	}

	@GetMapping("/add_connection.html")
	public String displayAddConnectionPage(@ModelAttribute FirstNameAndLastName firstNameAndLastName, Model model) {
		Iterable<User> users = userService.getUsers();
		
		
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

	@PostMapping("/searchUser")
	public String getUserByFirstNameAndLastName(FirstNameAndLastName firstNameAndLastName, Model model) {
		System.out.println(firstNameAndLastName.getFirstName());
		Optional<User> optUser = userService.getUser(firstNameAndLastName.getFirstName(),
				firstNameAndLastName.getLastName());
		User userId = optUser.get();
		System.out.println(userId.getFirstName());
		model.addAttribute("user", userId);
		return "add_connection";
	}

	@PostMapping("/saveOperation")
	public String saveOperation(@ModelAttribute Transaction transaction) {
		System.out.println("Je suis dans la methode saveOperation");
		System.out.println("Id de l'utilisateur connecté : " + currentUserId);
		System.out.println("Montant transmis : " + transaction.getAmount());
		System.out.println("Id du beneficiaire : " + transaction.getUserId());
		operationService.addOperation(currentUserId, transaction.getUserId(), transaction.getAmount());
		return "redirect:/";
	}
	
	@GetMapping("/save") /*A SUPPRIMER*/
	public String save() {
		System.out.println("Je suis dans la methode saveOperation");
		System.out.println("Id de l'utilisateur connecté : " + currentUserId);
		System.out.println("Id du bénéficiaire : " + beneficiary);
		System.out.println("Montant recuperé : " + amount);
		operationService.addOperation(currentUserId, beneficiary, amount);
		return "transfer";
	}

	@PostMapping("/getUser")
	public String/* ModelAndView */ getUser(Model model, int nombre) {
		log.info("Récupération d'un utilisateur");
		Optional<User> optUser = userService.getUser(nombre);
		User userId = optUser.get();
		System.out.println(userId.getFirstName());
		model.addAttribute("users", userId);
		return /* new ModelAndView */("index");
	}

	@PostMapping("/addBuddy")
	public ModelAndView addBuddy(@ModelAttribute FormAddConnectionTh formAddConnectionTh) {
		String wantedEmail = "";
		boolean loginMode = false;
		if(loginMode) {
			/*EMAIL RECUPERE VIA LA ZONE DE TEXTE*/
			wantedEmail = formAddConnectionTh.getLogin(); 
		}else
		{
			/*EMAIL RECUPERE VIA LA DROP DOWN*/
			wantedEmail = formAddConnectionTh.getBuddyEmail();
		}
		Optional<User> user = userService.findUser(wantedEmail);
		if(user.isEmpty()) {
			System.out.println("Utilisateur non trouvé");
			formComment.setMessage("l'email " + wantedEmail + " n'est pas connu dans la base de données");
			return new ModelAndView("redirect:/add_connection.html");
		} else {
			User currentUser = getCurrentUser();
			List<User> buddies = currentUser.getBuddies();
			formComment.setMessage("");
			/*VERIFIER SI L'AMI FAIT DEJA PARTI DE LA LISTE AVANT DE L'AJOUTER*/
			List<String> buddiesLogin = new ArrayList<>();
			for(User b : buddies) {
				buddiesLogin.add(b.getLogin());
			}
			if(buddiesLogin.contains(wantedEmail)) {
				System.out.println("Impossible d'ajouter : " + user.get().getFirstName());
			}
			else {
				userService.addBuddy(currentUserId, user.get().getId());
				System.out.println("Utilisateur ajouté : " + user.get().getFirstName());
			}
		}
		return new ModelAndView("redirect:/");
	}


	/*
	 * @PostMapping("/buddies") public String getUserBuddies(Model model, int id) {
	 * log.info("Récupération des amis"); Optional<User> optUser =
	 * userService.getUser(id); User userId1 = optUser.get(); List<Buddy> buddies =
	 * userId1.getBuddies(); for (Buddy b : buddies) {
	 * System.out.println(b.getFirstName()); } System.out.println(buddies.size());
	 * model.addAttribute("buddies", buddies); return "index"; }
	 */
}
