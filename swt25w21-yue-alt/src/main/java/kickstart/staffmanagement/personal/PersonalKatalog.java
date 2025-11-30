package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.AbstractEinstellbar;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;


/**
 * Katalog for {@link Personal} entities.
 */
public interface PersonalKatalog extends CrudRepository<Personal, Long> {

	/**
	 * Default sort order for {@link Personal} entities is by ID descending.
	 */
	Sort DEFAULT_SORT = Sort.sort(AbstractEinstellbar.class).by(AbstractEinstellbar::getId).descending();

	Streamable<Personal> findByType(PersonalTyp type, Sort sort);

	/**
	 * Find {@link Personal} entities by their {@link PersonalTyp}. Uses {@link #DEFAULT_SORT} as default sort order.
	 * @param type for later Sort to get specific type of staff members
	 * @return Streamable<Personal>
	 */
	default Streamable<Personal> findByType(PersonalTyp type) {return findByType(type, DEFAULT_SORT);
	}

	Streamable<Personal> findAll();


}