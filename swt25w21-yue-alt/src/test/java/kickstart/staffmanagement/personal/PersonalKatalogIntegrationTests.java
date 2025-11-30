package kickstart.staffmanagement.personal;
import kickstart.staffmanagement.EinstellStatus;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import kickstart.AbstractIntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.salespointframework.core.Currencies.EURO;

class PersonalKatalogIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	PersonalKatalog katalog;

	@Test
	void canSaveAndFindByType() {
		var catering1 = new CateringPersonal(
			"Anna", "Huber", "0123-456", "anna@festival.de",
			EinstellStatus.EINSTELLBAR, Money.of(15, EURO)
		);

		var catering2 = new CateringPersonal(
			"Ben", "Klein", "0123-789", "ben@festival.de",
			EinstellStatus.EINGESTELLT, Money.of(17, EURO)
		);

		katalog.save(catering1);
		katalog.save(catering2);

		var result = katalog.findByType(PersonalTyp.CATERING);

		assertThat(result)
			.extracting(Personal::getName)
			.contains("Huber", "Klein");
	}
}
