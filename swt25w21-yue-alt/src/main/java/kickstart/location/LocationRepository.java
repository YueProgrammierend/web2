package kickstart.location;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;


public interface LocationRepository extends CrudRepository<Location, LocationIdentifier> {
    
    public Location findByName(String name);

    @NotNull
	public Iterable<Location> findAll();


	@NotNull
	Optional<Location> findById(@NotNull LocationIdentifier  id);

}
