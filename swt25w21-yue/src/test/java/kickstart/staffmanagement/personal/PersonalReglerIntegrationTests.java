package kickstart.staffmanagement.personal;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import kickstart.AbstractIntegrationTests;
import kickstart.staffmanagement.EinstellStatus;

import static org.assertj.core.api.Assertions.assertThat;

class PersonalReglerIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	PersonalRegler controller;

	@Autowired
	PersonalKatalog katalog;

	Personal personal;

	@BeforeEach
	void setup() {
		katalog.deleteAll();
		personal = katalog.save(new SecurityPersonal(
			"Max", "Mustermann", "0176-999", "max@festival.de",
			EinstellStatus.EINSTELLBAR, Money.of(20, "EUR")
		));
	}

	@Test
	void showCatalogPageRendersCorrectViewAndModel() {
		var model = new ExtendedModelMap();

		String view = controller.showCatalog(model);

		assertThat(view).isEqualTo("personalkatalog");
		assertThat(model.asMap())
			.containsKeys("catalog", "types", "statuses");
	}

	@Test
	void updateStatusChangesPersonalStatus() {
		controller.updateStatus(personal.getId(), EinstellStatus.EINGESTELLT);

		var updated = katalog.findById(personal.getId()).orElseThrow();
		assertThat(updated.getStatus()).isEqualTo(EinstellStatus.EINGESTELLT);
	}
}
