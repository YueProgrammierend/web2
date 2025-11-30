package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PersonalRegler {

	private final PersonalKatalog personalKatalog;

	public PersonalRegler(PersonalKatalog personalKatalog) {
		this.personalKatalog = personalKatalog;
	}

	@GetMapping("/personalkatalog")
	public String showCatalog(Model model) {
		model.addAttribute("title", "Personalkatalog");
		model.addAttribute("catalog", personalKatalog.findAll());
		model.addAttribute("types", PersonalTyp.values());
		model.addAttribute("statuses", EinstellStatus.values());
		return "personalkatalog";
	}


	@PostMapping("/personal/{id}/status")
	public String updateStatus(@PathVariable("id") Long id,
							   @RequestParam EinstellStatus status) {
		personalKatalog.findById(id).ifPresent(personal -> {
			personal.setStatus(status);
			personalKatalog.save(personal);
		});
		return "redirect:/personalkatalog";
	}

	@PostMapping("/personal/new")
	public String createPersonal(
		@RequestParam String vorName,
		@RequestParam String nachName,
		@RequestParam String telefon,
		@RequestParam String email,
		@RequestParam PersonalTyp type,
		@RequestParam EinstellStatus status,
		@RequestParam double stundenlohn) {

		var lohn = Money.of(stundenlohn, "EUR");
		Personal p;

		switch (type) {
			case CATERING -> p = new CateringPersonal(vorName, nachName, telefon, email, status, lohn);
			case VERKAUF -> p = new VerkaufsPersonal(vorName, nachName, telefon, email, status, lohn);
			case SECURITY -> p = new SecurityPersonal(vorName, nachName, telefon, email, status, lohn);
			default -> throw new IllegalArgumentException("Unbekannter Personaltyp");
		}

		personalKatalog.save(p);
		return "redirect:/personalkatalog";
	}

}
