package kickstart.account;

//import kickstart.festival.*;
//import kickstart.location.*;
//import org.salespointframework.useraccount.*;

import kickstart.festival.FestivalManagement;
import kickstart.festival.FestivalRepository;
import kickstart.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


@Controller
public class UserController {
	@Autowired
	// UserAccountRepository repo;
	// 'org.salespointframework.useraccount.UserAccountRepository' is not public.
	// Cannot be accessed from outside package


	//	public final FestivalRepository festivalRepo;
	//	private final LocationRepository locationRepo;
	//	private final FestivalRepository festivalRepo;
	//	private final User userForAssigning;

	@GetMapping("/login")
	public String login() {
		return "login";
	}


	//Entsprechend der Rolle weiterleiten
	@GetMapping("/Logged")
	public String logged() {
		return "user-info";
	}


	/*@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("registrationForm", new User());
		return "register";
	}
	*/

}
