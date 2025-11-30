package kickstart.location;

import org.springframework.validation.Errors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class LocationForm {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    @Min(0)
    private Integer capacity;
    
    public LocationForm(){};

    public LocationForm(String name, String address, Integer capacity){
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public Integer getCapacity(){
        return capacity;
    }

    public void validate(Errors errors, LocationRepository locationRepository){
        //Validation
    }

}
