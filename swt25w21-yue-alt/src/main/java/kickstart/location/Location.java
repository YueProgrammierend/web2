package kickstart.location;


import java.util.UUID;

import org.salespointframework.core.AbstractAggregateRoot;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Location extends AbstractAggregateRoot<LocationIdentifier> {


    private @EmbeddedId LocationIdentifier id = new LocationIdentifier(UUID.randomUUID());

    private String name;
    private String address;
    private Integer capacity;

    //Default constructor for JPA
    protected Location() {}

    public Location(String name, String address, Integer capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public LocationIdentifier getId() {
        return id;
    }

    public String getLocationId() {
        return id.id().toString();
    }
}
