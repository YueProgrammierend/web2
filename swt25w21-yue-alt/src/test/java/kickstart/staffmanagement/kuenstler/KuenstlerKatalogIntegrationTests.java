package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.EinstellStatus;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import kickstart.AbstractIntegrationTests;


import static org.assertj.core.api.Assertions.assertThat;
import static org.salespointframework.core.Currencies.EURO;

class KuenstlerKatalogIntegrationTests extends AbstractIntegrationTests {

	@Autowired
	KuenstlerKatalog katalog;

	@Test
	void canSaveAndFindByType() {

		var solo1 = new SoloKuenstler(
			"Test",
			"Künstler1",
			"Test Künstler1",
			"0123456789",
			"test1@music.de",
			EinstellStatus.EINSTELLBAR,
			Money.of(100, EURO),
			1
		);

		var solo2 = new SoloKuenstler(
			"Test",
			"Künstler2",
			"Test Künstler2",
			"0123456790",
			"test2@music.de",
			EinstellStatus.EINSTELLBAR,
			Money.of(150, EURO),
			1
		);

		katalog.save(solo1);
		katalog.save(solo2);

		var result = katalog.findByType(KuenstlerTyp.SOLO);

		// Prüfen, dass die zwei Test-Künstler im Ergebnis vorkommen
		assertThat(result)
			.extracting(Kuenstler::getName)
			.contains("Test Künstler1", "Test Künstler2");
	}

}
