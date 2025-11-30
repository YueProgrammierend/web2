package kickstart.location;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class LocationController{
    
    private final LocationRepository locations;
    private final LocationManagement locationManagement;

	public LocationController(LocationManagement locationManagement, LocationRepository locations) {
		
		Assert.notNull(locations, "LocationRepository must not be null!");

		Assert.notNull(locationManagement, "LocationManagement must not be null!");

		this.locationManagement = locationManagement;
		this.locations = locations;
		
	}

	@GetMapping("/newlocation")
	String newLocation (Model model) {
		model.addAttribute("locationForm", new LocationForm());
		return "newlocation";
	}

	@PostMapping("/newlocation")
	String newLocation(
            @Valid LocationForm form,
            Errors result,
            Model model) {
		
		form.validate(result, locations);

		if (result.hasErrors()) {
			model.addAttribute("locations", locations.findAll());
			return "newlocation";
		}

		
		// (｡◕‿◕｡)
		// Falles alles in Ordnung ist legen wir einen Festival an
        locationManagement.newLocation(form);
        return "redirect:/location";
        
	}

	@GetMapping("/location")
	public String location(Model model) {
		Iterable<Location> allLocations = locationManagement.getAllLocations();
		model.addAttribute("locations", allLocations);
        System.out.println("Locations found: " + allLocations);
		return "location";
	}
}