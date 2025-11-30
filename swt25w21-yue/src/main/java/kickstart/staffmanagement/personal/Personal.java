package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.AbstractEinstellbar;
import kickstart.staffmanagement.EinstellStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.javamoney.moneta.Money;

/**
 * Entity representing internal festival staff members who management hires.
 * <p>
 * {@code Personal} extends {@link AbstractEinstellbar} and adds attributes specific to internal staff,
 * such as first name ({@code vorName}) and {@link PersonalTyp}.
 * </p>
 *
 * <p>Typical subclasses might represent specific roles like security, catering, or ticket sale staff.</p>
 *
 * <p>This class is marked as {@link Entity} and can be persisted via JPA.</p>
 *
 * @author Alexandra
 * @see AbstractEinstellbar
 * @see PersonalTyp
 * @see EinstellStatus
 */
@Entity
public abstract class Personal extends AbstractEinstellbar {

	private String vorName;

	@Enumerated(EnumType.STRING)
	protected PersonalTyp type;

	protected Personal() {}

	public Personal(String vorName, String nachName, String telefon, String email, EinstellStatus status,
					 Money hourlyRate) {
		super(nachName, telefon, email, status, hourlyRate);

		this.vorName = vorName;
	}

	public PersonalTyp getType() { return type; }

	public String getVorName() { return vorName; }

	public void setType(PersonalTyp type) {
		this.type = type;
	}
}