package kickstart.stageassignment;

import kickstart.bereichsplanung.BereichRepository;
import kickstart.bereichsplanung.Buehne;
import kickstart.staffmanagement.EinstellStatus;
import kickstart.staffmanagement.kuenstler.Kuenstler;
import kickstart.staffmanagement.kuenstler.KuenstlerKatalog;
import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/stages")
public class StageController {

	private final BereichRepository bereichRepository;
	private final KuenstlerKatalog kuenstlerRepository;
	private final StagePlanService stagePlanService;
	private final SlotRepo slotRepo;

	public StageController(
		BereichRepository bereichRepository,
		KuenstlerKatalog kuenstlerRepository,
		StagePlanService stagePlanService,
		SlotRepo slotRepo
	) {
		this.bereichRepository = bereichRepository;
		this.kuenstlerRepository = kuenstlerRepository;
		this.stagePlanService = stagePlanService;
		this.slotRepo = slotRepo;
	}

	@GetMapping("")
	public String listStages(Model model) {
		model.addAttribute("stages", bereichRepository.findAllStages());
		return "stages";
	}

	@GetMapping("/{id}")
	public String viewStage(@PathVariable Product.ProductIdentifier id, Model model) {
		Buehne buehne = readStage(id);
		fillModel(model, buehne);
		model.addAttribute("slotForm", new SlotForm());
		return "stages";
	}


	@PostMapping("/{id}/assign")
	public String assign(
		@PathVariable("id") Product.ProductIdentifier id,
		@ModelAttribute("slotForm") @Valid SlotForm form,
		BindingResult bindingResult,
		Model model
	) {

		Buehne buehne = readStage(id);
		fillModel(model, buehne);

		if (!form.isValidInterval()) {
			bindingResult.rejectValue("end", "invalid.interval", "Ende muss nach dem Start liegen.");
		}

		if (!form.isMinimumLength(30)) {
			bindingResult.rejectValue("end", "too.short", "Eine Stage muss mindestens 30 Minuten belegt sein.");
		}

		// if error, try entering with blank form again
		if (bindingResult.hasErrors()) {
			return "stages";
		}

		// assignment with error handling
		try {
			Kuenstler kuenstler = kuenstlerRepository.findById(form.getKuenstlerId()).orElseThrow();
			stagePlanService.assignKuenstlerToStage(buehne, kuenstler, form.getStart(), form.getEnd());
		} catch (IllegalArgumentException e) {
			bindingResult.reject("assignment.error", e.getMessage());
			return "stages";
		}

		return "redirect:/stages/" + id;
	}

	@PostMapping("/{stageId}/slot/{slotId}/remove")
	public String removeSlot(
		@PathVariable("stageId") Product.ProductIdentifier stageId,
		@PathVariable("slotId") Long slotId,
		Model model
	) {
		Buehne buehne = readStage(stageId);

		// Slot laden
		StageSlot slot = slotRepo.findById(slotId)
			.orElseThrow(() -> new IllegalArgumentException("Slot nicht gefunden"));

		// prüfen, ob der Slot wirklich zu dieser Bühne gehört
		if (!slot.getBuehne().equals(buehne)) {
			throw new IllegalArgumentException("Slot gehört nicht zu dieser Bühne!");
		}

		slotRepo.delete(slot);
		return "redirect:/stages/" + stageId;
	}

	private void fillModel(Model model, Buehne buehne) {
		model.addAttribute("selected", true);
		model.addAttribute("buehne", buehne);
		model.addAttribute("kuenstler", kuenstlerRepository.findByEinstellStatus(EinstellStatus.EINGESTELLT));
		model.addAttribute("slots", slotRepo.findByBuehne(buehne));
	}

	private Buehne readStage(Product.ProductIdentifier id) {
		return bereichRepository.findById(id)
			.map(b -> {
				if (!(b instanceof Buehne)) {
					throw new IllegalArgumentException("Bereich ist keine Bühne.");
				}
				return (Buehne) b;
			})
			.orElseThrow(() -> new IllegalArgumentException("Bereich nicht gefunden."));
	}



}
