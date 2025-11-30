package kickstart.stageassignment;

import kickstart.bereichsplanung.Buehne;
import jakarta.persistence.*;
import kickstart.staffmanagement.kuenstler.Kuenstler;

import java.time.LocalDateTime;

@Entity
public class StageSlot {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "buehne_id")
	private Buehne buehne;

	@ManyToOne
	private Kuenstler kuenstler;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	protected StageSlot() {}

	public StageSlot(Buehne buehne, Kuenstler kuenstler,
					 LocalDateTime startTime, LocalDateTime endTime) {
		this.buehne = buehne;
		this.kuenstler = kuenstler;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getId() { return id; }
	public Buehne getBuehne() { return buehne; }
	public Kuenstler getKuenstler() { return kuenstler; }
	public LocalDateTime getStartTime() { return startTime; }
	public LocalDateTime getEndTime() { return endTime; }
}
