package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import kickstart.AbstractIntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;

class KuenstlerReglerIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	KuenstlerRegler controller;

	@Autowired
	KuenstlerKatalog katalog;

	Kuenstler kuenstler;

	@BeforeEach
	void setup() {
		katalog.deleteAll();
		kuenstler = katalog.save(new SoloKuenstler(
			"Helene", "Fischer", "Helene Fischer",
			"0176-123", "helene@example.com",
			EinstellStatus.EINSTELLBAR, Money.of(1000, "EUR"), 1));
	}

	@Test
	void showCatalogPageRendersCorrectViewAndModel() {
		var model = new org.springframework.ui.ExtendedModelMap();
		String view = controller.showCatalog(null, model);

		assertThat(view).isEqualTo("kuenstlerkatalog");
		assertThat(model.asMap())
			.containsKeys("catalog", "soloArtists", "bands", "statuses");
	}

	@Test
	void updateStatusChangesArtistStatus() {
		controller.updateStatus(kuenstler.getId(), EinstellStatus.EINGESTELLT);

		var updated = katalog.findById(kuenstler.getId()).orElseThrow();
		assertThat(updated.getStatus()).isEqualTo(EinstellStatus.EINGESTELLT);
	}
}
