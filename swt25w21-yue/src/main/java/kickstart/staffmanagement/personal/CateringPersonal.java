package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing catering staff members.
 */
@Entity
public class CateringPersonal extends Personal {

	protected CateringPersonal() {}

	public CateringPersonal(String vorName, String nachName, String telefon, String email, EinstellStatus status, Money stundenlohn) {
		super(vorName, nachName,telefon, email, status, stundenlohn);

		this.type = PersonalTyp.CATERING;
	}


}
