package kickstart.festival;

//import kickstart.location.LocationRepository;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.annotation.Nullable;
import org.springframework.validation.Errors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FestivalForm {

    @NotBlank
    private String name;
    
    @NotNull
    private LocalDateTime startDate;
    
    @NotNull
    private LocalDateTime endDate;
    
    @NotBlank
    private String locationId;

    // Beim Erstellen einer leeren Tabelle
    public FestivalForm() {
    }
    // private final @NotEmpty String name;
    // private final @NotEmpty LocalDateTime startDate;
    // private final @NotEmpty LocalDateTime endDate;
    // private final @NotEmpty Location location;
    public FestivalForm(String name, LocalDateTime startDate, LocalDateTime endDate, String locationId ) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locationId  = locationId;
    }
    // Beim Erstellen einer neuen Tabelle und Ausfüllen eines im Repository vorhandenen Fests（Unten）

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getLocationId() {
        return locationId;
    }
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

	public void validate(Errors errors,
						 //LocationRepository locationRepo, // get location existiert, deshalb ist locationRepo nicht mehr nötig
						 FestivalRepository festivalRepo,
                         @Nullable FestivalIdentifier currentId){
		//hier Check für Location einfügen

        if (name.isBlank()) {
            errors.rejectValue("name", "festival.name.required",
                    "⚠️ Bitte den Name der Festival eingeben.");
        }

		if (startDate != null && endDate != null) {
			if (!startDate.isBefore(endDate)) {
				errors.rejectValue("endDate", "festival.time.false",
					"❌ Das Enddatum muss nach dem Startdatum liegen." );
			}
		}else{
			if(startDate == null) {
                //errors.rejectValue("endDate", "festival.time.invalid",
				errors.rejectValue("startDate", "festival.time.required",
					"⚠️ Bitte Beginndaten eingeben");
			}
			if(endDate == null) {
				errors.rejectValue("endDate", "festival.time.required",
					"⚠️ Bitte Enddaten eingeben");
			}
		}

		if (locationId == null || locationId.isBlank()) {
			errors.rejectValue("locationId", "location.required",
				"⚠️ Bitte eine Location auswählen.");
		}

		if (startDate != null && endDate != null) {
            for (Festival existing : festivalRepo.findAll()) {

                if (currentId != null && existing.getId().equals(currentId)) {
                    continue;
                }

				FestivalForm location = new FestivalForm(
					existing.getName(),
					existing.getStartDate(),
					existing.getEndDate(),
					existing.getLocation().getLocationId()
				);

				if (!this.isNotConflicting(this, location)) {
					errors.rejectValue("endDate", "festival.conflict",
						"❌ Die ausgewählte Zeit überschneidet sich mit einem bestehenden Festival an diesem Ort.");
					break;
				}
			}
		}
	}

	//  public boolean isBefore(FestivalForm appointment) {
	//     return appointment.getStartDate().isBefore(appointment.getEndDate());
	//  } //direct `java.time`'s isBefore() benutyen.  Methode nicht mehr unnötig

	boolean isNotConflicting(FestivalForm newFestival, FestivalForm localFestival) {
		if (!Objects.equals(newFestival.getLocationId(), localFestival.getLocationId())) {
			return true;
		}

		return newFestival.getEndDate().isBefore(localFestival.getStartDate())
			||newFestival.getStartDate().isAfter(localFestival.getEndDate());
		// isBefore first used，and then
		//newFestival.getStartDate().isBefore(location.getStartDate()
		//return newFestival.getEndDate().isAfter(location.getEndDate()); unnötig
	}

    // Beim Erstellen einer neuen Tabelle und Ausfüllen eines im Repository vorhandenen Fests（Unten）
    public FestivalForm(Festival festival) {
        this.name = festival.getName();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.locationId = festival.getLocation().getLocationId();
    }

}