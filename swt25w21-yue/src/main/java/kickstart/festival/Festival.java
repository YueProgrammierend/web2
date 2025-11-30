package kickstart.festival;

import kickstart.account.User;
import kickstart.location.Location;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;





@Entity
public class Festival {

    private @EmbeddedId FestivalIdentifier id = new FestivalIdentifier(UUID.randomUUID());
    
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private Location location;
//
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private User user;

    @SuppressWarnings("unused")
    private Festival() {}

    public Festival(String name, LocalDateTime startDate, LocalDateTime endDate, Location location) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Location getLocation() {
        return location;
    }

    public FestivalIdentifier getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}