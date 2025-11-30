package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.salespointframework.core.Currencies.EURO;

class BereichSubKlassenTest {

	private void assertCommonAttributes(
		Bereich b,
		String name,
		double price,
		int posX,
		int posY,
		int width,
		int height,
		boolean blocked
	) {
		assertEquals(name, b.getName());
		assertEquals(price, b.getPrice().getNumber().doubleValue());
		assertEquals(posX, b.getPositionX());
		assertEquals(posY, b.getPositionY());
		assertEquals(width, b.getWidth());
		assertEquals(height, b.getHeight());
		assertEquals(blocked, b.getBlocked());
	}

	@Test
	void testCamping() {
		Camping c = new Camping(
			"Campingplatz",
			Money.of(25, EURO),
			10, 20,
			30, 40,
			true,
			5,
			false  );

		assertCommonAttributes(c, "Campingplatz", 25.0, 10, 20, 30, 40, false);

		assertTrue(c.getRented());
		assertEquals(5, c.getPitchCount());

		c.setRented(false);
		c.setPitchCount(99);
		c.setBlocked(true);

		assertFalse(c.getRented());
		assertEquals(99, c.getPitchCount());
		assertTrue(c.getBlocked());
	}

	@Test
	void testCatering() {
		Catering cat = new Catering(
			"Foodstand",
			Money.of(100, EURO),
			5, 10,
			15, 20,
			true,
			false
		);

		assertCommonAttributes(cat, "Foodstand", 100.0, 5, 10, 15, 20, false);

		assertTrue(cat.getRented());

		cat.setRented(false);
		cat.setBlocked(true);

		assertFalse(cat.getRented());
		assertTrue(cat.getBlocked());
	}

	@Test
	void testToiletten() {
		Toiletten t = new Toiletten(
			"WC-Haus",
			Money.of(12, EURO),
			1, 2,
			3, 4,
			true,
			8,
			false
		);

		assertCommonAttributes(t, "WC-Haus", 12.0, 1, 2, 3, 4, false);

		assertTrue(t.getRented());
		assertEquals(8, t.getStallCount());

		t.setRented(false);
		t.setStallCount(99);
		t.setBlocked(true);

		assertFalse(t.getRented());
		assertEquals(99, t.getStallCount());
		assertTrue(t.getBlocked());
	}

	@Test
	void testErsteHilfeZelt() {
		ErsteHilfeZelt z = new ErsteHilfeZelt(
			"EHZ",
			Money.of(5, EURO),
			9, 8,
			7, 6,
			true,
			false
		);

		assertCommonAttributes(z, "EHZ", 5.0, 9, 8, 7, 6, false);

		assertTrue(z.getRented());

		z.setRented(false);
		z.setBlocked(true);

		assertFalse(z.getRented());
		assertTrue(z.getBlocked());
	}

	@Test
	void testWiese() {
		Wiese w = new Wiese(
			"Grasfläche",
			10, 20,
			30, 40,
			true
		);

		assertCommonAttributes(w, "Grasfläche", 0.0, 10, 20, 30, 40, true);

		w.setBlocked(false);
		assertFalse(w.getBlocked());
	}


	@Test
	void testPolymorphie() {
		Bereich b = new Catering(
			"Polymorph",
			Money.of(50, EURO),
			1, 1,
			1, 1,
			true,
			false
		);

		assertEquals("Polymorph", b.getName());
		assertTrue(((Catering) b).getRented());
	}
}
