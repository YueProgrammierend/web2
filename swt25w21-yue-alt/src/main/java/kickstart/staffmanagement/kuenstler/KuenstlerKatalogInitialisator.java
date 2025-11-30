package kickstart.staffmanagement.kuenstler;

import static org.salespointframework.core.Currencies.*;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Data initializer for the {@link KuenstlerKatalog}.
 * <p>
 * This component populates the {@link KuenstlerKatalog} with default artists (solo artists and bands)
 * when the application starts. Entries are only created if the catalog is empty.
 * </p>
 *
 * <p>Each artist entry includes a name, telephone number, email address, {@link EinstellStatus} (hiring status),
 * and an hourly rate or performance fee represented by {@link Money}.</p>
 *
 * <p>Typical usage: artist instances created here are stored in {@link KuenstlerKatalog} and
 * can later be displayed or updated via the web interface.</p>
 *
 * <p>Note: this initializer currently creates a predefined set of example artists for demonstration purposes.</p>
 *
 * @author Alexandra
 * @see KuenstlerKatalog
 * @see DataInitializer
 * @see EinstellStatus
 * @see Kuenstler
 */
@Component
@Order(20)
public class KuenstlerKatalogInitialisator implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(KuenstlerKatalogInitialisator.class);

	private final KuenstlerKatalog kuenstlerKatalog;

	/**
	 * Creates a new {@link KuenstlerKatalogInitialisator} for the given {@link KuenstlerKatalog}.
	 * @param kuenstlerKatalog
	 */
	KuenstlerKatalogInitialisator(KuenstlerKatalog kuenstlerKatalog) {

		Assert.notNull(kuenstlerKatalog, "kickstart.KuenstlerKatalog must not be null!");

		this.kuenstlerKatalog = kuenstlerKatalog;
	}

	/**
	 * Creates default artist entries in the {@link KuenstlerKatalog}.
 	 */
	@Override
	public void initialize() {

		if (kuenstlerKatalog.findAll().iterator().hasNext()) {
			return;
		}

		LOG.info("Creating default catalog entries.");

		kuenstlerKatalog.save(
			new Band(
				"Rammstein",
				"0301234567",
				"contact@rammstein.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(1500, EURO),
				5 // number of technicians
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Helene",
				"Fischer",
				"Helene Fischer",
				"01761234567",
				"helene@music.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(10000, EURO),
				2 // technicians
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Die Toten Hosen",
				"0309876543",
				"info@dth.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(1300, EURO),
				4
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Coldplay",
				"03011223344",
				"contact@coldplay.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(2000, EURO),
				6
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Mark",
				"Forster",
				"Mark Forster",
				"01769876543",
				"mark@music.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(750, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Lena",
				"Meyer-Landrut",
				"Lena Meyer-Landrut",
				"01761239876",
				"lena@music.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(700, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Tim",
				"Bendzko",
				"Tim Bendzko",
				"01761234588",
				"tim@music.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(680, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Green Day",
				"0301000001",
				"contact@greenday.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(1800, EURO),
				5
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Imagine Dragons",
				"0301000002",
				"contact@imaginedragons.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(2000, EURO),
				6
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Sleep Token",
				"0301000004",
				"contact@sleeptoken.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(1600, EURO),
				4
			)
		);

		kuenstlerKatalog.save(
			new Band(
				"Die Prinzen",
				"0301000005",
				"contact@dieprinzen.de",
				EinstellStatus.EINSTELLBAR,
				Money.of(1400, EURO),
				3
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Albus",
				"Dumbledore",
				"Fresh D",
				"0176000006",
				"albus@dumbledore.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(420, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Wolfgang Amadeus",
				"Mozart",
				"Wolfgang Amadeus Mozart",
				"0176000008",
				"mozart@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(0, EURO),
				0
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Johann",
				"Hölzel",
				"Falco",
				"0176000007",
				"falco@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(10, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Udo",
				"Jürgens",
				"Udo Jürgens",
				"0176000009",
				"udo@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(1200, EURO),
				1
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Orpheus",
				"",
				"Orpheus",
				"0176000010",
				"orpheus@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(1, EURO),
				0
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Dieter",
				"Bohlen",
				"Dieter Bohlen",
				"0176000011",
				"dieter@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(500000, EURO),
				2
			)
		);

		kuenstlerKatalog.save(
			new SoloKuenstler(
				"Florian",
				"Silbereisen",
				"Florian Silbereisen",
				"0176000012",
				"florian1@music.com",
				EinstellStatus.EINSTELLBAR,
				Money.of(850, EURO),
				1
			)
		);
	}
}



