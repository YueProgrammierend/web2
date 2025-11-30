package kickstart.staffmanagement.kuenstler;

import kickstart.staffmanagement.kuenstler.KuenstlerKatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import kickstart.AbstractIntegrationTests;

import static org.assertj.core.api.Assertions.assertThat;

class KuenstlerKatalogInitialisatorTests extends AbstractIntegrationTests {

	@Autowired
	KuenstlerKatalog katalog;

	@Autowired
	@Qualifier("kuenstlerKatalogInitialisator")
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
