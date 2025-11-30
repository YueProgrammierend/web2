package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.AbstractEinstellbar;
import kickstart.staffmanagement.EinstellStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;


/**
 * Katalog for {@link Kuenstler} entities.
 */
public interface KuenstlerKatalog extends CrudRepository<Kuenstler, Long> {

	/**
	 * Default sort order for {@link Kuenstler} entities is by ID descending.
	 */
	Sort DEFAULT_SORT = Sort.sort(AbstractEinstellbar.class).by(AbstractEinstellbar::getId).descending();


	Streamable<Kuenstler> findByType(KuenstlerTyp type, Sort sort);

	/**
	 * Find {@link Kuenstler} entities by their {@link KuenstlerTyp}. Uses {@link #DEFAULT_SORT} as default sort order.
	 * @param type
	 * @return
	 */
	default Streamable<Kuenstler> findByType(KuenstlerTyp type) {
		return findByType(type, DEFAULT_SORT);
	}


	Streamable<Kuenstler> findAll();

	Streamable<Kuenstler> findByEinstellStatus(EinstellStatus status);

}