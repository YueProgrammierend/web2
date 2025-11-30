package kickstart.staffmanagement.kuenstler;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import kickstart.staffmanagement.EinstellStatus;

import static org.assertj.core.api.Assertions.*;

class KuenstlerDomainTests {

	@Test
	void bandCorrectAttributesTest() {
		var band = new Band(
			"Metallica",
			"0123-456789",
			"metallica@example.com",
			EinstellStatus.EINGESTELLT,
			Money.of(120, "EUR"),
			3
		);

		assertThat(band.getType()).isEqualTo(KuenstlerTyp.BAND);
		assertThat(band.getTechnicianCount()).isEqualTo(3);
		assertThat(band.getName()).isEqualTo("Metallica");
		assertThat(band.getEmail()).isEqualTo("metallica@example.com");
		assertThat(band.getStundenlohn().isPositive()).isTrue();
	}

	@Test
	void soloKuenstlerCorrectAttributesTest() {
		var solo = new SoloKuenstler(
			"Freddie",
			"Mercury",
			"Freddie Mercury",
			"0987-654321",
			"freddie@example.com",
			EinstellStatus.EINSTELLBAR,
			Money.of(150, "EUR"),
			1
		);

		assertThat(solo.getVorName()).isEqualTo("Freddie");
		assertThat(solo.getNachName()).isEqualTo("Mercury");
		assertThat(solo.getName()).isEqualTo("Freddie Mercury");
		assertThat(solo.getType()).isEqualTo(KuenstlerTyp.SOLO);
		assertThat(solo.getTechnicianCount()).isEqualTo(1);
	}

	@Test
	void stundenlohnAndStatusAreInheritedFromEinstellbar() {
		var solo = new SoloKuenstler(
			"Billie",
			"Eilish",
			"Billie Eilish",
			"1234-9876",
			"billie@example.com",
			EinstellStatus.ABGELEHNT,
			Money.of(90, "EUR"),
			2
		);

		assertThat(solo.getStundenlohn()).isEqualTo(Money.of(90, "EUR"));
		assertThat(solo.getStatus()).isEqualTo(EinstellStatus.ABGELEHNT);
	}
}
