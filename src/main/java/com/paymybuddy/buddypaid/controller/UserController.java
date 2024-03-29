package com.paymybuddy.buddypaid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.service.IUserAccountService;
import com.paymybuddy.buddypaid.service.IUserService;
import com.paymybuddy.buddypaid.service.PaginationService;
import com.paymybuddy.buddypaid.workclasses.CurrentUser;
import com.paymybuddy.buddypaid.workclasses.DisplayedOperation;
import com.paymybuddy.buddypaid.workclasses.FormAddConnectionTh;
import com.paymybuddy.buddypaid.workclasses.FormComment;
import com.paymybuddy.buddypaid.workclasses.ModifiedUser;
import com.paymybuddy.buddypaid.workclasses.TextArea;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	private IUserService userService;
	private FormComment contactFormComment = new FormComment();
	private FormComment addConnectionFormComment = new FormComment();
	private CurrentUser currentUser;
	private PaginationService paginationService;
	private IUserAccountService userAccountService;
	
	public UserController(IUserService userService, CurrentUser currentUser, PaginationService paginationService, IUserAccountService userAccountService) {
		this.userService = userService;
		this.currentUser = currentUser;
		this.paginationService = paginationService;
		this.userAccountService = userAccountService;
	}
	
	@GetMapping("/")
	public String redirectToLogin() {
		log.info("redirecting to login page");
		return "redirect:/login";
	}
	
	@GetMapping("/transfer")
	public String displayTransferPage(Model model, @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page) {
		User user = currentUser.getCurrentUser();
		List<User> buddies = user.getBuddies();
		List<Operation> operations = user.getOperations();

		/* ALLER CHERCHER LE BUDDY FIRSTNAME POUR CHAQUE OPERATION */
		model.addAttribute("operations", operations);
		model.addAttribute("buddies", buddies);
		List<DisplayedOperation> displayedOperations = new ArrayList<>();
		for (Operation operation : operations) {
			if (operation.getBuddyId() == 1) { /*BANK USERID IS ALWAYS 1*/
				/*NE PAS AJOUTER DANS LA LISTE*/
			} else {
				int buddyId = operation.getBuddyId();
				Optional<User> optUser = userService.getUser(buddyId);
				User u = optUser.get();
				displayedOperations.add(new DisplayedOperation(u.getFirstName(), operation.getDescription(), operation.getAmount()));
			}
		}
		
		int currentPage = page.orElse(1);
        int pageSize = 3;

        Page<DisplayedOperation> partialDisplayedOperations = paginationService.findPaginated(PageRequest.of(currentPage - 1, pageSize), displayedOperations);
       
        model.addAttribute("displayedOperations", partialDisplayedOperations);

        int totalPages = partialDisplayedOperations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		log.info("display transfer page");
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
		log.info("display page to search and add a connection");
		return "add_connection";
	}

	@GetMapping("/home")
	public String displayHomePage(Model model) {
		User user = currentUser.getCurrentUser();
		long amountBalance = Math.round(userAccountService.getBalance(user.getId()));
		String displayedAmount = String.valueOf(amountBalance) + "€";
		model.addAttribute("amount", displayedAmount);
		log.info("display home page");
		return "home";
	}

	@GetMapping("/profile")
	public String displayProfilePage(Model model) {
		User user = currentUser.getCurrentUser();
		model.addAttribute("user", user);
		log.info("display profile page");
		return "profile";
	}
	
	@PostMapping("/modifyProfile")
	public String modifyProfile(@ModelAttribute ModifiedUser modifiedUser) {
		User user = currentUser.getCurrentUser();
		userService.saveUser(user.getId(), user.getLogin(), user.getPassword(), modifiedUser.getFirstName(), modifiedUser.getLastName());
		log.info("redirecting to profile page");
		return "redirect:/profile";
	}
	
	@GetMapping("/contact")
	public String displayContactPage(Model model) {
		model.addAttribute("formComment", contactFormComment);
		log.info("display contact page");
		return "contact";
	}
	
	@PostMapping("/submitDemand")
	public String submitDemand(@ModelAttribute TextArea textArea) {
		/*FONCTIONNALITE DE TRAITEMENT D'UNE DEMANDE UTILISATEUR*/
		if(textArea.getMessage().isEmpty()) {
			contactFormComment.setError(true);
			contactFormComment.setMessage("Your demand must not be empty");
			log.error("user message is empty");
		} else {
			log.info("user message is valid");
			contactFormComment.setError(false);
			contactFormComment.setMessage("We successfully received your inquiry.\r\n"
					+ "We will process it as soon as possible.");
		}
		log.info("redirecting to contact page");
		return "redirect:/contact";
	}

	@GetMapping("/logoff")
	public String displayLogOff() {
		log.info("display logoff page");
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
			log.error("User is not found in database");
			addConnectionFormComment.setError(true);
			addConnectionFormComment.setMessage("The email " + wantedEmail + " is unknown in the database");
			return new ModelAndView("redirect:/add_connection");
		} else {
			log.info("User is found in database");
			User authenticatedUser = currentUser.getCurrentUser();
			List<User> buddies = authenticatedUser.getBuddies();
			addConnectionFormComment.setMessage("");
			/* VERIFIER SI L'AMI FAIT DEJA PARTI DE LA LISTE AVANT DE L'AJOUTER */
			List<String> buddiesLogin = new ArrayList<>();
			for (User b : buddies) {
				buddiesLogin.add(b.getLogin());
			}
			if (buddiesLogin.contains(wantedEmail)) {
				log.error("Impossible to add : " + user.get().getFirstName());
				addConnectionFormComment.setError(true);
				addConnectionFormComment.setMessage(user.get().getFirstName() + " is already one of your buddy");
			} else {
				userService.addBuddy(authenticatedUser.getId(), user.get().getId());
				log.info("User added : " + user.get().getFirstName());
				addConnectionFormComment.setError(false);
				addConnectionFormComment.setMessage("You added " + user.get().getFirstName() + " to your buddies");
			}
		}
		log.info("redirecting to the add_connection page");
		return new ModelAndView("redirect:/add_connection");
	}
}