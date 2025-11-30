package kickstart.festival;

import jakarta.annotation.PostConstruct;
import kickstart.location.Location;
import kickstart.location.LocationRepository;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class FestivalInitializer {

	private final FestivalRepository festRepo;
	private final LocationRepository locationRepo;

	public FestivalInitializer(FestivalRepository festRepo, LocationRepository locationRepo) {
		this.festRepo = festRepo;
		this.locationRepo = locationRepo;
	}

	@PostConstruct
	public void initialize() {
		Iterable<Location> loc = locationRepo.findAll();

		Location location;
		if (!loc.iterator().hasNext()) {
			location = locationRepo.save(new Location("Stadion Dresden", "Gustav-Esche-Strasse 7, 01219 Dresden", 500));
		} else {
			location = loc.iterator().next();
		}

		if (festRepo.count() == 0) {
			LocalDateTime thisMoment = LocalDateTime.now();
			LocalDate today = LocalDate.now();
			LocalDateTime todayEnd = today.atTime(23, 59, 59);
			festRepo.save(new Festival("a Crazy Party!", thisMoment, todayEnd, location));
			festRepo.save(new Festival("Mathe Seminar", thisMoment.plusDays(1), thisMoment.plusDays(1).plusHours(2), location));
			festRepo.save(new Festival("🎇Musik Festival🎇",
				today.plusDays(3).atTime(0, 0, 0),todayEnd.plusDays(7), location));

		}
	}
}
