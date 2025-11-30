package kickstart.bereichsplanung;

import org.salespointframework.catalog.Catalog;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BereichRepository extends Catalog<Bereich> {

	@Query("SELECT b FROM Buehne b")
	List<Buehne> findAllStages();

}
