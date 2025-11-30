package kickstart.festival;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import kickstart.location.Location;
import kickstart.location.LocationIdentifier;
import kickstart.location.LocationRepository;
import kickstart.tickets.TicketInitializer;
import kickstart.tickets.TicketManagement;

@Service
@Transactional
public class FestivalManagement {

    private final FestivalRepository festivals;
    private final LocationRepository locationRepository;
    private final TicketInitializer ticketInitializer;
    private final TicketManagement ticketManagement;

    public FestivalManagement(FestivalRepository festivals, LocationRepository locationRepository, TicketInitializer ticketInitializer, TicketManagement ticketManagement) {
        this.festivals = festivals;
        this.locationRepository = locationRepository;
        this.ticketInitializer = ticketInitializer;
        this.ticketManagement = ticketManagement;
    }

    public Festival createFestival(FestivalForm form) {
        Assert.notNull(form, "FestivalForm must not be null!");

        //Sammelstelle für Errors
        Errors errors = new BeanPropertyBindingResult(form, "festivalForm");

        //Errorhandling im FestivalForm
        form.validate(errors, festivals, null);

        if (errors.hasErrors()) {
            //Hier einfügen, was bei Fehler passieren soll
            throw new IllegalArgumentException("Validation failed");
        }

        UUID locationUUID = UUID.fromString(form.getLocationId());
        Location location = locationRepository.findById(new LocationIdentifier(locationUUID)).orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + form.getLocationId()));

        Festival festival = festivals.save(new Festival(
                form.getName(),
                form.getStartDate(),
                form.getEndDate(),
                location
        ));

        ticketInitializer.createTickets(festival);

        return festival;
    }

    // Festivals anzeigen
    public Iterable<Festival> getAllFestivals() {
        return festivals.findAll();
    }


    public Iterable<Festival> getFindToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(23, 59, 59);
        return festivals.findToday(todayStart, todayEnd);
    }

    public Iterable<Festival> getFindOnlyToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(23, 59, 59);
        return festivals.findOnlyToday(todayStart, todayEnd);
    }


    public void updateFestival(FestivalIdentifier id, FestivalForm form) {
        Errors errors = new BeanPropertyBindingResult(form, "festivalForm");
        form.validate(errors, festivals, id);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Validation failed");
        }
        Festival festival = festivals.findById(id).orElseThrow();
        festival.setName(form.getName());
        festival.setStartDate(form.getStartDate());
        festival.setEndDate(form.getEndDate());

        UUID locationUUID = UUID.fromString(form.getLocationId());
        Location location = locationRepository.findById(new LocationIdentifier(locationUUID)).orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + form.getLocationId()));
        festival.setLocation(location);
        festivals.save(festival);
    }

    @NotNull
    public FestivalForm changeFestivalForm(FestivalForm form, Festival festThisID) {
        FestivalForm formChange = new FestivalForm(festThisID);
        if (!Objects.equals(form.getName(), festThisID.getName())) {
            formChange.setName(form.getName());
        }
        if (form.getStartDate() != null
                && !(form.getStartDate().isEqual(festThisID.getStartDate()))) {
            formChange.setStartDate(form.getStartDate());
        }
        if (form.getEndDate() != null
                && !(form.getEndDate().isEqual(festThisID.getEndDate()))) {
            formChange.setEndDate(form.getEndDate());
        }

        if (form.getLocationId() != null && !form.getLocationId().isBlank()
                && !Objects.equals(
                UUID.fromString(form.getLocationId()), festThisID.getLocation().getId().id())
        ) {
            formChange.setLocationId(form.getLocationId());
        }
        return formChange;
    }

    @Transactional
    public void deleteFestival(FestivalIdentifier id) {
        ticketManagement.deleteTicketsByFestival(id);
        festivals.deleteById(id);
    }
}