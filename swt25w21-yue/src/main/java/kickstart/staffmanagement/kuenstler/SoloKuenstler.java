package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import jakarta.persistence.Entity;

/**
 * Entity representing artists who work alone.
 */
@Entity
public class SoloKuenstler extends Kuenstler {

	private String vorName, nachName;

	protected SoloKuenstler() {}

	public SoloKuenstler(String vorName, String nachName, String stageName,
						 String telefon, String email, EinstellStatus status,
						 Money stundenlohn, int technicianCount) {
		super(stageName, telefon, email, status, stundenlohn, technicianCount);

		this.vorName = vorName;
		this.nachName = nachName;
		this.type = KuenstlerTyp.SOLO;
	}

	public String getVorName() {
		return vorName;
	}

	public String getNachName() {
		return nachName;
	}

}
