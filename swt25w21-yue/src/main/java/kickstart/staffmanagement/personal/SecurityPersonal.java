package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing security staff members. There needs to be around 1 security staff member per 100 guests
 */
@Entity
public class SecurityPersonal extends Personal {

	protected SecurityPersonal() {}

	public SecurityPersonal(String vorName, String nachName, String telefon, String email, EinstellStatus status, Money stundenlohn) {
		super(vorName, nachName,telefon, email, status, stundenlohn);

		this.type = PersonalTyp.SECURITY;
	}


}
