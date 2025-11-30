package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.salespointframework.core.Currencies.EURO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BereichManagerTest {

	@Autowired
	BereichManager manager;

	@Autowired
	BereichRepository repository;

	@BeforeEach
	void reset() {
		repository.deleteAll();
	}

	@Test
	void testSaveAndFindAll() {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			1, 2,
			3, 4,
			false,
			5,
			false
		);

		manager.save(c);

		assertEquals(1, manager.findAll().size());
	}

	@Test
	void testFindById() {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			1, 2,
			3, 4,
			false,
			5,
			false
		);

		manager.save(c);

		assertTrue(manager.findById(c.getId().toString()).isPresent());
	}

	@Test
	void testDeleteById() {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			1, 2,
			3, 4,
			false,
			5,
			false
		);

		manager.save(c);

		manager.deleteById(c.getId().toString());

		assertEquals(0, manager.findAll().size());
	}

	@Test
	void testCalculateTotalRentalCost() {

		Camping c = new Camping(
			"C",
			Money.of(10, EURO),
			1, 1,
			1, 1,
			false,
			2,
			false
		);

		Catering cat = new Catering(
			"Cat",
			Money.of(20, EURO),
			1, 1,
			1, 1,
			false,
			false);

		Toiletten t = new Toiletten(
			"WC",
			Money.of(5, EURO),
			1, 1,
			1, 1,
			false,
			2,
			false
		);

		repository.save(c);
		repository.save(cat);
		repository.save(t);

		double sum = manager.calculateTotalRentalCost();

		assertEquals(35.0, sum);
	}
}
