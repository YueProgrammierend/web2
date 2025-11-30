package kickstart.staffmanagement.personal;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.salespointframework.core.Currencies.EURO;

/**
 * Data initializer for the {@link PersonalKatalog}.
 * <p>
 * This component populates the {@link PersonalKatalog} with default staff entries
 * (security personnel, sales staff, catering staff) when the application starts.
 * It only creates entries if the catalog is empty.
 * </p>
 *
 * <p>Each staff entry is created with a first and last name, phone number, email,
 * {@link EinstellStatus} (hiring status), and hourly rate.</p>
 *
 * @author Alexandra
 * @see PersonalKatalog
 * @see DataInitializer
 * @see Order
 * @see EinstellStatus
 */
@Component
@Order(20)
public class PersonalKatalogInitialisator implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(PersonalKatalogInitialisator.class);

	private final PersonalKatalog personalKatalog;

	/**
	 * Creates a new {@link PersonalKatalogInitialisator} for the given {@link PersonalKatalog}.
	 * @param personalKatalog
	 */
	PersonalKatalogInitialisator(PersonalKatalog personalKatalog) {

		Assert.notNull(personalKatalog, "kickstart.PersonalKatalog must not be null!");

		this.personalKatalog = personalKatalog;
	}


	/**
	 * Creates default staff entries in the {@link PersonalKatalog}.
	 */
	@Override
	public void initialize() {

		if (personalKatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default catalog entries.");

		personalKatalog.save(
			new SecurityPersonal(
				"Anna",
				"Schulz",
				"01761234567",
				"anna.schulz@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(18, EURO)
			)
		);

		personalKatalog.save(
			new SecurityPersonal(
				"Kevin",
				"Müller",
				"01761230000",
				"kevin.mueller@festival.de",
				EinstellStatus.EINGESTELLT,
				Money.of(20, EURO)
			)
		);

		personalKatalog.save(
			new VerkaufsPersonal(
				"Lisa",
				"Becker",
				"01764567890",
				"lisa.becker@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(15, EURO)
			)
		);

		personalKatalog.save(
			new VerkaufsPersonal(
				"Tom",
				"Schneider",
				"01761239876",
				"tom.schneider@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(16, EURO)
			)
		);

		personalKatalog.save(
			new CateringPersonal(
				"Laura",
				"Hoffmann",
				"01767894532",
				"laura.hoffmann@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(17, EURO)
			)
		);

		personalKatalog.save(
			new CateringPersonal(
				"Ben",
				"Kaiser",
				"01769998877",
				"ben.kaiser@festival.de",
				EinstellStatus.EINGESTELLT,
				Money.of(19, EURO)
			)
		);

		personalKatalog.save(
			new VerkaufsPersonal(
				"Sophie",
				"Fischer",
				"01761112233",
				"sophie.fischer@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(14, EURO)
			)
		);

		personalKatalog.save(
			new CateringPersonal(
				"Max",
				"Meier",
				"01765554433",
				"max.meier@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(18, EURO)
			)
		);

		personalKatalog.save(
			new SecurityPersonal(
				"Patrick",
				"Kowalski",
				"01763334455",
				"patrick.kowalski@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(21, EURO)
			)
		);

		personalKatalog.save(
			new VerkaufsPersonal(
				"Julia",
				"Lang",
				"01764446677",
				"julia.lang@festival.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(15, EURO)
			)
		);

	}


}



