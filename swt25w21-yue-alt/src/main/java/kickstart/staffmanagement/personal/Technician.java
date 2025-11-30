package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing technicians. Artist decides how many there are for their gig.
 */
@Entity
public class Technician extends Personal {

	protected Technician() {}

	public Technician(String vorName, String nachName, String telefon, String email, EinstellStatus status, Money stundenlohn) {
		super(vorName, nachName,telefon, email, status, stundenlohn);

		this.type = PersonalTyp.TECHNIKER;
	}


}
