package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.salespointframework.core.Currencies.EURO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class BereichControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	BereichRepository repository;

	@BeforeEach
	void cleanup() {
		repository.deleteAll();
	}

	@Test
	@WithMockUser
	void testList() throws Exception {
		mvc.perform(get("/bereiche"))
			.andExpect(status().isOk())
			.andExpect(view().name("bereiche"))
			.andExpect(model().attributeExists("bereiche"));
	}

	@Test
	@WithMockUser
	void testEditBereich() throws Exception {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			10, 10,
			10, 10,
			false,
			5,
			false
		);

		repository.save(c);

		mvc.perform(get("/bereiche/bearbeiten/" + c.getId().toString()))
			.andExpect(status().isOk())
			.andExpect(view().name("bereich-bearbeiten"))
			.andExpect(model().attributeExists("bereich"));
	}


	@Test
	@WithMockUser
	void testBlockBereich() throws Exception {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			10, 10,
			10, 10,
			false,
			5,
			false
		);
		repository.save(c);

		mvc.perform(post("/bereiche/block")
				.param("id", c.getId().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bereiche"));

		Camping updated = (Camping) repository.findById(c.getId()).get();

		assert updated.getBlocked();
	}


	@Test
	@WithMockUser
	void testUpdate() throws Exception {

		Camping c = new Camping(
			"Test",
			Money.of(10, EURO),
			1, 1,
			1, 1,
			false,
			5,
			false
		);

		repository.save(c);

		mvc.perform(post("/bereiche/update")
				.param("id", c.getId().toString())
				.param("blocked", "true")
				.param("positionX", "50")
				.param("positionY", "60")
				.param("width", "70")
				.param("height", "80"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bereiche"));

		Camping updated = (Camping) repository.findById(c.getId()).get();

		assert updated.getBlocked();
		assert updated.getPositionX() == 50;
		assert updated.getPositionY() == 60;
		assert updated.getWidth() == 70;
		assert updated.getHeight() == 80;
	}
}
