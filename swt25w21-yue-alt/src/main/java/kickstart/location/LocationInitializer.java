package kickstart.location;

import jakarta.annotation.PostConstruct;
import kickstart.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LocationInitializer {
	private final LocationRepository locations;

	public LocationInitializer(LocationRepository locations) {
		this.locations = locations;
	}
	@PostConstruct
	public void init() {

		if (locations.count() == 0) {
			locations.save(new Location("Stadion Dresden", "Gustav-Esche-Strasse 7, 01219 Dresden", 50000));
			locations.save(new Location("Hirsch", "Kamenzer Strasse 18, 01099 Dresden", 1500));
			locations.save(new Location("Groovestation", "Louisenstrasse 39, 01099 Dresden", 800));
		}
	}
}
