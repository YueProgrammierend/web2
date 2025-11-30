package kickstart.location;

import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.mysema.commons.lang.Assert;

@Component
@Order(10)
public class LocationDataInitializer implements DataInitializer{

    private static final Logger LOG = LoggerFactory.getLogger(LocationDataInitializer.class);
    
    private LocationManagement locationManagement;
    
    public LocationDataInitializer(LocationManagement locationManagement){

        Assert.notNull(locationManagement, "LocationManagement must not be null");

        this.locationManagement = locationManagement;
    }


    public void initialize(){
        
        LOG.info("Locations werden hinzugefügt");

        List.of(
            new LocationForm("Stadion Dresden", "Gustav-Esche-Strasse 7, 01219 Dresden", 50000),
            new LocationForm("Hirsch", "Kamenzer Strasse 18, 01099 Dresden", 1500),
            new LocationForm("Groovestation", "Louisenstrasse 39, 01099 Dresden", 800)
            ).forEach(locationManagement::newLocation);
    }
}