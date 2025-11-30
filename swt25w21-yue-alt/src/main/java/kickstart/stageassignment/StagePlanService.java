package kickstart.stageassignment;

import kickstart.bereichsplanung.Buehne;
import kickstart.staffmanagement.kuenstler.Kuenstler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class StagePlanService {

	private final SlotRepo slotRepo;

	public StagePlanService(SlotRepo slotRepo) {
		this.slotRepo = slotRepo;
	}

	@Transactional
	public StageSlot assignKuenstlerToStage(Buehne buehne, Kuenstler kuenstler, LocalDateTime start, LocalDateTime end) {

		if (buehne == null) throw new IllegalArgumentException("Bühne darf nicht null sein");
		if (kuenstler == null) throw new IllegalArgumentException("Kuenstler darf nicht null sein!");
		if (start == null) throw new IllegalArgumentException("Start darf nicht null sein!");
		if (end == null) throw new IllegalArgumentException("Ende darf nicht null sein");
		if (!start.isBefore(end)) throw new IllegalArgumentException("Start must be before end");

		if (Duration.between(start, end).toMinutes() < 30) {
			throw new IllegalArgumentException("Slot muss mindestens 30 Minuten dauern!");
		}

		for (StageSlot slot : slotRepo.findByKuenstler(kuenstler)) {
			if (overlaps(slot.getStartTime(), slot.getEndTime(), start, end)) {
				throw new IllegalArgumentException("Künstler spielt bereits zu dieser Zeit auf einer anderen Bühne!");
			}
		}

		for (StageSlot slot : slotRepo.findByBuehne(buehne)) {
			if (overlaps(slot.getStartTime(), slot.getEndTime(), start, end)) {
				throw new IllegalArgumentException("Bühne wird bereits in diesem Slot genutzt!");
			}
		}

		StageSlot slot = new StageSlot(buehne, kuenstler, start, end);
		return slotRepo.save(slot);
	}


	private boolean overlaps(LocalDateTime s1, LocalDateTime e1,
							 LocalDateTime s2, LocalDateTime e2) {
		return !e1.isEqual(s2) && !e2.isEqual(s1) && s1.isBefore(e2) && s2.isBefore(e1);
	}

}
