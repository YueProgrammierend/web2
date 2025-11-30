package kickstart.stageassignment;

import kickstart.bereichsplanung.Buehne;
import kickstart.staffmanagement.kuenstler.Kuenstler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

public interface SlotRepo extends CrudRepository<StageSlot, Long> {

	Streamable<StageSlot> findByBuehne(Buehne buehne);

	Streamable<StageSlot> findByKuenstler(Kuenstler kuenstler);

}

