package kickstart.stageassignment;

import kickstart.AbstractIntegrationTests;
import kickstart.bereichsplanung.BereichRepository;
import kickstart.bereichsplanung.Buehne;
import kickstart.staffmanagement.EinstellStatus;
import kickstart.staffmanagement.kuenstler.Band;
import kickstart.staffmanagement.kuenstler.KuenstlerKatalog;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.core.Currencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
class StageControllerIntegrationTest extends AbstractIntegrationTests {

	@Autowired MockMvc mvc;
	@Autowired BereichRepository bereichRepository;
	@Autowired KuenstlerKatalog kuenstlerKatalog;
	@Autowired SlotRepo slotRepo;

	private Buehne buehne;
	private Band band;

	@BeforeEach
	void setup() {
		// Create a stage
		buehne = bereichRepository.save(new Buehne(
			"Main Stage", 0,0,10,10,false
		));

		// Create artist
		band = kuenstlerKatalog.save(new Band(
			"Metallica",
			"1234",
			"mail@test.de",
			EinstellStatus.EINGESTELLT,
			Money.of(30, Currencies.EURO),
			1
		));
	}

	@Test
	void testListStages() throws Exception {
		mvc.perform(get("/stages"))
			.andExpect(status().isOk())
			.andExpect(view().name("stages"))
			.andExpect(model().attributeExists("stages"));
	}

	@Test
	void testViewStage() throws Exception {
		mvc.perform(get("/stages/" + buehne.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("stages"))
			.andExpect(model().attributeExists("buehne"))
			.andExpect(model().attributeExists("kuenstler"))
			.andExpect(model().attributeExists("slots"))
			.andExpect(model().attributeExists("slotForm"));
	}

	@Test
	void testAssignValidSlot() throws Exception {

		mvc.perform(
				post("/stages/" + buehne.getId() + "/assign")
					.param("kuenstlerId", String.valueOf(band.getId()))
					.param("start", "2030-01-01T12:00:00")
					.param("end", "2030-01-01T13:00:00")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/stages/" + buehne.getId()));

		// Slot persisted?
		var slots = slotRepo.findByBuehne(buehne);
		assert(slots.iterator().hasNext());
	}

	@Test
	void testAssignInvalidInterval() throws Exception {

		mvc.perform(
				post("/stages/{id}/assign", buehne.getId())
					.param("kuenstlerId", String.valueOf(band.getId()))
					.param("start", "2030-01-01T14:00:00")
					.param("end", "2030-01-01T13:00:00")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("stages"))
			.andExpect(model().hasErrors());
	}



	@Test
	void testRemoveSlot() throws Exception {
		// create a slot manually
		var slot = slotRepo.save(
			new StageSlot(
				buehne,
				band,
				LocalDateTime.of(2030, 1,1, 12,0),
				LocalDateTime.of(2030, 1,1, 13,0)
			)
		);

		mvc.perform(
				post("/stages/" + buehne.getId() + "/slot/" + slot.getId() + "/remove")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/stages/" + buehne.getId()));

		// slot removed?
		assert(slotRepo.findById(slot.getId()).isEmpty());
	}

	@Test
	void testAssignOverlappingSlot() throws Exception {

		slotRepo.save(new StageSlot(
			buehne,
			band,
			LocalDateTime.of(2030,1,1,12,0),
			LocalDateTime.of(2030,1,1,13,0)
		));

		mvc.perform(
				post("/stages/" + buehne.getId() + "/assign")
					.param("kuenstlerId", String.valueOf(band.getId()))
					.param("start", "2030-01-01T12:30:00")
					.param("end",   "2030-01-01T13:30:00")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("stages"))
			.andExpect(model().hasErrors());
	}

	@Test
	void testAssignMissingArtist() throws Exception {
		mvc.perform(
				post("/stages/" + buehne.getId() + "/assign")
					.param("start", "2030-01-01T12:00:00")
					.param("end", "2030-01-01T13:00:00")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("stages"))
			.andExpect(model().hasErrors());
	}

	@Test
	void testAssignInvalidDateFormat() throws Exception {
		mvc.perform(
				post("/stages/" + buehne.getId() + "/assign")
					.param("kuenstlerId", String.valueOf(band.getId()))
					.param("start", "not-a-date")
					.param("end", "not-a-date")
			)
			.andExpect(status().isOk());
	}
	@Test
	void testAssignSlotExactBoundaryOverlap() throws Exception {
		// Existing slot
		slotRepo.save(new StageSlot(
			buehne, band,
			LocalDateTime.of(2030, 1,1, 12,0),
			LocalDateTime.of(2030, 1,1, 13,0)
		));

		// Slot ends exactly when existing slot starts (should succeed)
		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
				.param("kuenstlerId", String.valueOf(band.getId()))
				.param("start", "2030-01-01T11:00:00")
				.param("end", "2030-01-01T12:00:00")
			).andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/stages/" + buehne.getId()));

		// Slot starts exactly when existing slot ends (should succeed)
		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
				.param("kuenstlerId", String.valueOf(band.getId()))
				.param("start", "2030-01-01T13:00:00")
				.param("end", "2030-01-01T14:00:00")
			).andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/stages/" + buehne.getId()));
	}

	@Test
	void testAssignMultipleArtistsDifferentSlots() throws Exception {
		Band anotherBand = kuenstlerKatalog.save(new Band(
			"Iron Maiden", "5678", "iron@test.de",
			EinstellStatus.EINGESTELLT, Money.of(40, Currencies.EURO), 1
		));

		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
			.param("kuenstlerId", String.valueOf(band.getId()))
			.param("start", "2030-01-01T10:00:00")
			.param("end", "2030-01-01T11:00:00")
		).andExpect(status().is3xxRedirection());

		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
			.param("kuenstlerId", String.valueOf(anotherBand.getId()))
			.param("start", "2030-01-01T11:00:00")
			.param("end", "2030-01-01T12:00:00")
		).andExpect(status().is3xxRedirection());

		var slots = slotRepo.findByBuehne(buehne);
		assert(slots.stream().anyMatch(s -> s.getKuenstler().equals(band)));
		assert(slots.stream().anyMatch(s -> s.getKuenstler().equals(anotherBand)));
	}


	@Test
	void testAssignSlotEmptyStartOrEnd() throws Exception {
		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
				.param("kuenstlerId", String.valueOf(band.getId()))
				.param("start", "")
				.param("end", "2030-01-01T13:00:00")
			).andExpect(status().isOk())
			.andExpect(model().hasErrors());

		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
				.param("kuenstlerId", String.valueOf(band.getId()))
				.param("start", "2030-01-01T12:00:00")
				.param("end", "")
			).andExpect(status().isOk())
			.andExpect(model().hasErrors());
	}

	@Test
	void testAssignSlotStartEqualsEnd() throws Exception {
		mvc.perform(post("/stages/" + buehne.getId() + "/assign")
				.param("kuenstlerId", String.valueOf(band.getId()))
				.param("start", "2030-01-01T12:00:00")
				.param("end", "2030-01-01T12:00:00")
			).andExpect(status().isOk())
			.andExpect(model().hasErrors());
	}

}
