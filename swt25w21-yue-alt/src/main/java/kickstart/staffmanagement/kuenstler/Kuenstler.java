package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.AbstractEinstellbar;
import kickstart.staffmanagement.EinstellStatus;import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.javamoney.moneta.Money;



/**
 * Abstract base class representing an artist hired for the festival.
 * <p>
 * {@code Kuenstler} extends {@link AbstractEinstellbar} and adds the {@link KuenstlerTyp} field,
 * which distinguishes between different types of artists (e.g., solo artists or bands).
 * </p>
 *
 * <p>Each artist has identifying information such as name, telephone number, email address,
 * hiring status ({@link EinstellStatus}), and hourly wage.</p>
 *
 * <p>This class is marked as {@link Entity} and is persisted via JPA.
 * Concrete subclasses should represent specific types of artists.</p>
 *
 * <p>Typical usage: artist instances are stored in {@link KuenstlerKatalog} and
 * managed via the web interface for viewing and updating their hiring status.</p>
 *
 * @author Alexandra
 * @see AbstractEinstellbar
 * @see KuenstlerTyp
 * @see EinstellStatus
 */
@Entity
public abstract class Kuenstler extends AbstractEinstellbar {

	@Enumerated(EnumType.STRING)
	protected KuenstlerTyp type;

	protected int technicianCount = 0;

	protected Kuenstler() {}

	public Kuenstler(String name, String telefon, String email, EinstellStatus status,
					 Money stundenlohn, int technicianCount) {
		super(name, telefon, email, status, stundenlohn);

		this.technicianCount = technicianCount;
	}

	public int getTechnicianCount() { return technicianCount; }
	public KuenstlerTyp getType() { return type; }
}
