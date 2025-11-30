package kickstart.staffmanagement.kuenstler;
import kickstart.staffmanagement.EinstellStatus;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>This controller interacts with {@link KuenstlerKatalog} to fetch and persist artist data.</p>
 *
 * <p>Typical usage: the {@code /kuenstlerkatalog} page shows the catalog and allows
 * the festival administrator to update artist hiring statuses directly from the web interface.</p>
 *
 * @author Alexandra
 * @see KuenstlerKatalog
 * @see EinstellStatus
 * @see KuenstlerTyp
 */
@Controller
public class KuenstlerRegler {

	private final KuenstlerKatalog kuenstlerKatalog;

	public KuenstlerRegler(KuenstlerKatalog kuenstlerKatalog) {
		this.kuenstlerKatalog = kuenstlerKatalog;
	}

	/**
	 * Displays the artist catalog page.
	 * <p>
	 * Adds attributes to the {@link Model} for rendering in Thymeleaf:
	 * <ul>
	 *     <li>{@code title} – the page title</li>
	 *     <li>{@code catalog} – all artists</li>
	 *     <li>{@code soloArtists} – artists of type {@link KuenstlerTyp#SOLO}</li>
	 *     <li>{@code bands} – artists of type {@link KuenstlerTyp#BAND}</li>
	 *     <li>{@code statuses} – all possible hiring statuses ({@link EinstellStatus})</li>
	 * </ul>
	 *
	 * @param model the Spring MVC model to which attributes are added
	 * @return the name of the Thymeleaf template to render ("kuenstlerkatalog")
	 */
	@GetMapping("/kuenstlerkatalog")
	public String showCatalog(@RequestParam(required = false) String type, Model model) {
		populateModel(model, type);
		model.addAttribute("title", "Künstlerkatalog");
		return "kuenstlerkatalog";
	}

	@PostMapping("/artist/{id}/status")
	public String updateStatus(@PathVariable("id") Long id,
							   @RequestParam EinstellStatus status) {
		kuenstlerKatalog.findById(id).ifPresent(artist -> {
			artist.setStatus(status);
			kuenstlerKatalog.save(artist);
		});
		return "redirect:/kuenstlerkatalog";
	}


	@PostMapping("/artist/add")
	public String addArtist(
		@RequestParam String type,
		@RequestParam String name,
		@RequestParam(required = false) String vorName,
		@RequestParam(required = false) String nachName,
		@RequestParam String email,
		@RequestParam String telefon,
		@RequestParam double stundenlohn,
		@RequestParam int technicianCount,
		@RequestParam(required = false) EinstellStatus status,
		Model model) {

		// Status auf Default setzen
		EinstellStatus finalStatus = (status != null) ? status : EinstellStatus.EINSTELLBAR;

		// Validierung
		if (email == null || !email.matches(".+@.+\\..+")) {
			model.addAttribute("error", "Bitte geben Sie eine gültige E-Mail-Adresse ein.");
			populateModel(model, type);
			return "kuenstlerkatalog";
		}

		if (name == null || name.trim().isEmpty()) {
			model.addAttribute("error", "Der Name darf nicht leer sein.");
			populateModel(model, type);
			return "kuenstlerkatalog";
		}

		if (telefon == null || telefon.trim().isEmpty()) {
			model.addAttribute("error", "Bitte geben Sie eine Telefonnummer ein.");
			populateModel(model, type);
			return "kuenstlerkatalog";
		}

		if (stundenlohn <= 0) {
			model.addAttribute("error", "Der Stundenlohn muss größer als 0 sein.");
			populateModel(model, type);
			return "kuenstlerkatalog";
		}

		try {
			if ("SoloKuenstler".equals(type)) {
				SoloKuenstler kuenstler = new SoloKuenstler(
					vorName, nachName, name, telefon.trim(), email.trim(),finalStatus, Money.of(stundenlohn, "EUR"),
					technicianCount);
				kuenstlerKatalog.save(kuenstler);
			} else if ("Band".equals(type)) {
				Band band = new Band(
					name, telefon.trim(), email.trim(),
					finalStatus, Money.of(stundenlohn, "EUR"), technicianCount
				);
				kuenstlerKatalog.save(band);
			} else {
				model.addAttribute("error", "Unbekannter Künstler-Typ.");
				populateModel(model, type);
				return "kuenstlerkatalog";
			}
		} catch (Exception e) {
			model.addAttribute("error", "Fehler beim Speichern des Künstlers: " + e.getMessage());
			populateModel(model, type);
			return "kuenstlerkatalog";
		}

		return "redirect:/kuenstlerkatalog";
	}

	private void populateModel(Model model, String type) {
		model.addAttribute("catalog", kuenstlerKatalog.findAll());
		model.addAttribute("soloArtists", kuenstlerKatalog.findByType(KuenstlerTyp.SOLO));
		model.addAttribute("bands", kuenstlerKatalog.findByType(KuenstlerTyp.BAND));
		model.addAttribute("statuses", EinstellStatus.values());
		model.addAttribute("type", type);
	}
}
