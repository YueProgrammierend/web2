package kickstart.staffmanagement.personal;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import kickstart.staffmanagement.EinstellStatus;

import static org.assertj.core.api.Assertions.*;

class PersonalDomainTests {

	@Test
	void cateringPersonalCorrectAttributesTest() {
		var catering = new CateringPersonal(
			"Anna", "Müller", "0123111222", "anna@example.com",
			EinstellStatus.EINGESTELLT, Money.of(20, "EUR")
		);

		assertThat(catering.getType()).isEqualTo(PersonalTyp.CATERING);
		assertThat(catering.getVorName()).isEqualTo("Anna");
		assertThat(catering.getName()).isEqualTo("Müller");
		assertThat(catering.getStundenlohn()).isEqualTo(Money.of(20, "EUR"));
		assertThat(catering.getStatus()).isEqualTo(EinstellStatus.EINGESTELLT);
	}

	@Test
	void securityPersonalTypeTest() {
		var sec = new SecurityPersonal(
			"Bob", "Schmidt", "0456999888", "bob@example.com",
			EinstellStatus.EINSTELLBAR, Money.of(18, "EUR")
		);

		assertThat(sec.getType()).isEqualTo(PersonalTyp.SECURITY);
		assertThat(sec.getVorName()).isEqualTo("Bob");
		assertThat(sec.getName()).isEqualTo("Schmidt");
	}

	@Test
	void technicianTypeTest() {
		var tech = new Technician(
			"Clara", "Weber", "0999222333", "clara@example.com",
			EinstellStatus.ABGELEHNT, Money.of(25, "EUR")
		);

		assertThat(tech.getType()).isEqualTo(PersonalTyp.TECHNIKER);
		assertThat(tech.getVorName()).isEqualTo("Clara");
		assertThat(tech.getStatus()).isEqualTo(EinstellStatus.ABGELEHNT);
	}

	@Test
	void verkaufsPersonalTypeTest() {
		var verkauf = new VerkaufsPersonal(
			"David", "Koch", "0111555444", "david@example.com",
			EinstellStatus.EINGESTELLT, Money.of(19, "EUR")
		);

		assertThat(verkauf.getType()).isEqualTo(PersonalTyp.VERKAUF);
		assertThat(verkauf.getVorName()).isEqualTo("David");
		assertThat(verkauf.getName()).isEqualTo("Koch");
	}
}
