package kickstart.location;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;


@Service
@Transactional
public class LocationManagement {

    private final LocationRepository locations;
     
    public LocationManagement(LocationRepository locationRepository) {
        this.locations = locationRepository;
    }
    
    public Location newLocation(LocationForm form){
        Assert.notNull(form, "LocationForm must not be null!");

        //Sammelstelle für Errors
        Errors errors = new BeanPropertyBindingResult(form, "festivalForm");

        //Errorhandling im LocationForm
        form.validate(errors, locations);

        if (errors.hasErrors()) {
            //Hier einfügen, was bei Fehler passieren soll
            throw new IllegalArgumentException("Validation failed");
        }

        return locations.save(new Location(
            form.getName(),
            form.getAddress(),
            form.getCapacity()
        ));
    }

    // Locations anzeigen
    public Iterable <Location> getAllLocations() {
        return locations.findAll();
    }
}
