package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.salespointframework.core.Currencies.EURO;


@Controller
@RequestMapping("/bereiche")
public class BereichController {

	private final BereichRepository repository;
	private final BereichInitializerManager initializer;

	public BereichController(BereichRepository repository, BereichInitializerManager initializer) {
		this.repository = repository;
		this.initializer = initializer;
	}

	@GetMapping
	String list(Model model) {
		model.addAttribute("bereiche", repository.findAll());
		return "bereiche";
	}

	@PostMapping("init/Bereichsplan1")
	public String initBereichsplan1() {
		initializer.initializePlan1();
		return "redirect:/bereiche";
	}

	@PostMapping("init/Bereichsplan2")
	public String initBereichsplan2() {
		initializer.initializePlan2();
		return "redirect:/bereiche";
	}

	@PostMapping("init/Bereichsplan3")
	public String initBereichsplan3() {
		initializer.initializePlan3();
		return "redirect:/bereiche";
	}

	@PostMapping("init/Bereichsplan4")
	public String initBereichsplan4() {
		initializer.initializePlan4();
		return "redirect:/bereiche";
	}

	@PostMapping("init/Bereichsplan5")
	public String initBereichsplan5() {
		initializer.initializePlan5();
		return "redirect:/bereiche";
	}

	@PostMapping("init/Bereichsplan6")
	public String initBereichsplan6() {
		initializer.initializePlan6();
		return "redirect:/bereiche";
	}


	@GetMapping("/bearbeiten/{id}")
	public String editBereich(@PathVariable("id") Product.ProductIdentifier id, Model model) {
		Bereich bereich = repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Ungültige Bereich ID: " + id));
		model.addAttribute("bereich", bereich);
		return "bereich-bearbeiten";
	}


	@PostMapping("/block")
	public String blockBereiche(@RequestParam("id") Bereich bereich) {
		bereich.setBlocked(true);
		repository.save(bereich);
		return "redirect:/bereiche";
	}

	@PostMapping("/unlock")
	public String unlockBereiche(@RequestParam("id") Bereich bereich) {
		bereich.setBlocked(false);
		repository.save(bereich);
		return "redirect:/bereiche";
	}

	@PostMapping("/update-rented")
	public String saveBereiche(@RequestParam("id") Product.ProductIdentifier id, @RequestParam boolean blocked, @RequestParam boolean rented, @RequestParam int positionX, @RequestParam int positionY, @RequestParam int width, @RequestParam int height, @RequestParam String price) {

		Bereich bereich = repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Ungültige Bereich ID: " + id));

		bereich.setBlocked(blocked);
		bereich.setPositionX(positionX);
		bereich.setPositionY(positionY);
		bereich.setWidth(width);
		bereich.setHeight(height);

		Money priceMoney;
		try {

			String priceClean = price.replace(",", ".");
			BigDecimal amount = new BigDecimal(priceClean);
			priceMoney = Money.of(amount, EURO);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Ungültiger Preis: " + price);
		}

		// Rented-spezifische Properties
		if (bereich instanceof Camping) {
			((Camping) bereich).setRented(rented);
			((Camping) bereich).setPrice(priceMoney);
		} else if (bereich instanceof Toiletten) {
			((Toiletten) bereich).setRented(rented);
			((Toiletten) bereich).setPrice(priceMoney);
		} else if (bereich instanceof Catering) {
			((Catering) bereich).setRented(rented);
			((Catering) bereich).setPrice(priceMoney);
		} else if (bereich instanceof ErsteHilfeZelt) {
			((ErsteHilfeZelt) bereich).setRented(rented);
			((ErsteHilfeZelt) bereich).setPrice(priceMoney);
		}
		repository.save(bereich);
		return "redirect:/bereiche";
	}

	@PostMapping("/update")
	public String saveBereiche(@RequestParam("id") Product.ProductIdentifier id,
							   @RequestParam boolean blocked,
							   @RequestParam int positionX,
							   @RequestParam int positionY,
							   @RequestParam int width,
							   @RequestParam int height) {

		Bereich bereich = repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Ungültige Bereich ID: " + id));

		bereich.setBlocked(blocked);
		bereich.setPositionX(positionX);
		bereich.setPositionY(positionY);
		bereich.setWidth(width);
		bereich.setHeight(height);

		repository.save(bereich);
		return "redirect:/bereiche";
	}
}
