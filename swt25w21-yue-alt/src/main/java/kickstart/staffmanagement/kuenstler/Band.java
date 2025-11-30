package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing bands.
 */
@Entity
public class Band extends Kuenstler {

	protected Band() {}

	public Band(String bandName, String telefon, String email, EinstellStatus status, Money stundenlohn, int technicianCount) {
		super(bandName,telefon, email, status, stundenlohn, technicianCount);

		this.type = KuenstlerTyp.BAND;
	}


}
