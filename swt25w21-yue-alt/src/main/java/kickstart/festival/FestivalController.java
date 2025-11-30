package kickstart.festival;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import kickstart.location.LocationRepository;


@Controller
public class FestivalController {

	private final LocationRepository locations;
	private final FestivalRepository festivalRepo;
    private final FestivalManagement festivalManagement;

	FestivalController(FestivalManagement festivalManagement,
                       LocationRepository locations,
					   FestivalRepository festivalRepo) {
		
		Assert.notNull(locations, "LocationRepository must not be null!");

		Assert.notNull(festivalManagement, "FestivalManagement must not be null!");

		this.festivalManagement = festivalManagement;
		this.locations = locations;
		this.festivalRepo = festivalRepo;
	}

	@GetMapping("/createfestival")
    public String creatfestival (Model model) {
		model.addAttribute("festivalForm", new FestivalForm());
		model.addAttribute("locations", locations.findAll());
		return "createfestival";
	}

	@PostMapping("/createfestival")
    public String createNew(
            @Valid FestivalForm form,
            Errors result,
            Model model) {

		form.validate(result, festivalRepo, null);
		if (result.hasErrors()) {
			model.addAttribute("locations", locations.findAll());
			return "createfestival";
		}

		try {
			// (｡◕‿◕｡)
			// Falles alles in Ordnung ist legen wir ein Festival an
            festivalManagement.createFestival(form);
            return "redirect:/festival";
        } catch (Exception e) {
            model.addAttribute("locations", locations.findAll());
            return "createfestival";
        }
	}

	@GetMapping("/festival")
    public String festival(Model model) {
		Iterable<Festival> allFestivals = festivalManagement.getAllFestivals();
		model.addAttribute("festivals", allFestivals);
		return "festival";
	}

    @GetMapping("/festival/today")
    public String showTodayFestivals(Model model) {
        Iterable<Festival> FestivalToday = festivalManagement.getFindToday();
        model.addAttribute("festivals", FestivalToday);
        return "festival";
    }

    @GetMapping("/festival/only-today")
    public String showOnlyTodayFestivals(Model model) {
        Iterable<Festival> FestivalToday = festivalManagement.getFindOnlyToday();
        model.addAttribute("festivals", FestivalToday);
        return "festival";
    }

    @GetMapping("/festival/edit/{uuid}")
    public String editFestival(@PathVariable("uuid") UUID uuid, Model model) {
		FestivalIdentifier id = new FestivalIdentifier(uuid);
        Festival fest = festivalRepo.findById(id).orElseThrow();
        FestivalForm form = new FestivalForm(fest);
        model.addAttribute("festival", fest);
        model.addAttribute("festivalForm", form);
        model.addAttribute("locations", locations.findAll());
        return "edit-festival";
    }

    @PostMapping("/festival/edit/error/{uuid}")
    public String updatefestival(@PathVariable("uuid") UUID uuid,
                                 @ModelAttribute("festivalForm") FestivalForm form,
								 Errors result,
                                 Model model) {

        FestivalIdentifier id = new FestivalIdentifier(uuid);
        Festival festThisID = festivalRepo.findById(id).orElseThrow();
        FestivalForm formChange = festivalManagement.changeFestivalForm(form, festThisID);

        formChange.validate(result, festivalRepo, id);
        if (result.hasErrors()) {
            model.addAttribute("festival", festThisID);
            model.addAttribute("festivalForm", form);
            model.addAttribute("locations", locations.findAll());
            return "edit-festival";
        }
        try {
            festivalManagement.updateFestival(id, formChange);
            return "redirect:/festival/edit/" + uuid;
        } catch (Exception e) {
            model.addAttribute("festival", festThisID);
            model.addAttribute("festivalForm", form);
            model.addAttribute("locations", locations.findAll());
            return "edit-festival";
        }
    }



    @PostMapping("/festival/delete/{id}")
    public String deleteFestival(@PathVariable("id") FestivalIdentifier id) {
        festivalManagement.deleteFestival(id);
        return "redirect:/festival";
    }
}
