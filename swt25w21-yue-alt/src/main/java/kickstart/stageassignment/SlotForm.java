package kickstart.stageassignment;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class SlotForm {

	@NotNull
	private Long kuenstlerId;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime start;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime end;


	public boolean isValidInterval() {
		return start != null && end != null && end.isAfter(start);
	}

	public boolean isMinimumLength(int minutes) {
		return start != null && end != null &&
			end.isAfter(start.plusMinutes(minutes));
	}


	public Long getKuenstlerId() {return kuenstlerId;}
	public LocalDateTime getStart() {return start;}
	public LocalDateTime getEnd() {return end;}

	public void setKuenstlerId(Long kuenstlerId) {this.kuenstlerId = kuenstlerId;}
	public void setStart(LocalDateTime start) {this.start = start;}
	public void setEnd(LocalDateTime end) {this.end = end;}

}
