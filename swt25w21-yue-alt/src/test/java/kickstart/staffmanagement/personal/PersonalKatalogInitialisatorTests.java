package kickstart.staffmanagement.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import kickstart.AbstractIntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;

class PersonalKatalogInitialisatorTests extends AbstractIntegrationTests {

	@Autowired
	PersonalKatalog katalog;

	@Autowired
	@Qualifier("personalKatalogInitialisator")
	DataInitializer init;

	@BeforeEach
	void setUp() {
		katalog.deleteAll();
	}

	@Test
	void createsInitialDataWhenEmpty() {
		init.initialize();
		assertThat(katalog.findAll()).isNotEmpty();
	}

	@Test
	void doesNotDuplicateDataWhenAlreadyInitialized() {
		init.initialize();
		long countAfterFirst = katalog.findAll().stream().count();
		init.initialize();
		long countAfterSecond = katalog.findAll().stream().count();

		assertThat(countAfterSecond).isEqualTo(countAfterFirst);
	}
}
