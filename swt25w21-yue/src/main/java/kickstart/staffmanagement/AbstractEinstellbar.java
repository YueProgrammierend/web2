package kickstart.staffmanagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.javamoney.moneta.Money;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Abstract base class representing external staff members who are hired for a festival.
 * <p>
 * This class provides common fields and methods for all staff members or artists hired externally. It is annotated with {@link MappedSuperclass},
 * meaning its fields are inherited by JPA entities extending it.
 * </p>
 *
 * <p>Each staff member has identifying information such as name, telephone number,
 * and email address, as well as their hiring status and hourly wage.</p>
 *
 * @author Alexandra
 * @see EinstellStatus
 */
@MappedSuperclass
public abstract class AbstractEinstellbar {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** Either the last name of the staff member or the stage name of the artist(s). */
	@NotBlank(message = "Name darf nicht leer sein")
	@Column(nullable = false)
	protected String name;

	@NotNull
	@Column(nullable = false)
	protected Money stundenlohn;

	@NotBlank
	@Column(nullable = false)
	protected String telefon;

	@NotBlank
	@Email(regexp = ".+@.+\\..+", message = "Ungültige E-Mail-Adresse")
	@Column(nullable = false, unique = true)
	protected String email;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	protected EinstellStatus einstellStatus;

	protected AbstractEinstellbar() {}

	/**
	 *
	 * @param name
	 * @param telefon
	 * @param email
	 * @param einstellStatus
	 * @param stundenlohn
	 */
	public AbstractEinstellbar(String name, String telefon, String email, EinstellStatus einstellStatus, Money stundenlohn) {
		this.name = name;
		this.stundenlohn = stundenlohn;
		this.telefon = telefon;
		this.email = email.trim();
		this.einstellStatus = einstellStatus;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getEmail() {
		return email;
	}

	public EinstellStatus getStatus() {
		return einstellStatus;
	}

	public void setStatus(EinstellStatus status) {
		this.einstellStatus = status;
	}

	public Money getStundenlohn() {
		return stundenlohn;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EinstellStatus getStaffStatus() {
		return einstellStatus;
	}


}
