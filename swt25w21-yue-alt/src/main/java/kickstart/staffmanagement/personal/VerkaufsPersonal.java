package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing ticket sale staff members.
 */
@Entity
public class VerkaufsPersonal extends Personal {

	protected VerkaufsPersonal() {}

	public VerkaufsPersonal(String vorName, String nachName, String telefon, String email, EinstellStatus status, Money stundenlohn) {
		super(vorName, nachName,telefon, email, status, stundenlohn);

		this.type = PersonalTyp.VERKAUF;
	}


}
