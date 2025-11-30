package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.salespointframework.core.Currencies.EURO;

class BereichTest {

	static class DummyBereich extends Bereich {
		public DummyBereich() {
			super("Test", Money.of(5, EURO), 1, 2, 3, 4, false);
		}
	}

	@Test
	void testGetterSetter() {

		Bereich b = new DummyBereich();

		b.setPositionX(11);
		b.setPositionY(22);
		b.setWidth(33);
		b.setHeight(44);
		b.setBlocked(true);

		assertEquals(11, b.getPositionX());
		assertEquals(22, b.getPositionY());
		assertEquals(33, b.getWidth());
		assertEquals(44, b.getHeight());
		assertTrue(b.getBlocked());
	}
}
