package kickstart.stageassignment;

import kickstart.bereichsplanung.Buehne;
import kickstart.staffmanagement.kuenstler.Kuenstler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Streamable;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

class StagePlanServiceTest {

	private SlotRepo slotRepo;
	private StagePlanService service;

	private Buehne buehne;
	private Kuenstler artist;

	@BeforeEach
	void setup() {
		slotRepo = mock(SlotRepo.class);
		service = new StagePlanService(slotRepo);

		buehne = mock(Buehne.class);
		artist = mock(Kuenstler.class);
	}

	@Test
	void assign_ok_whenNoConflicts() {
		when(slotRepo.findByBuehne(buehne)).thenReturn(Streamable.empty());
		when(slotRepo.findByKuenstler(artist)).thenReturn(Streamable.empty());

		LocalDateTime s = LocalDateTime.of(2025, 1, 1, 10, 0);
		LocalDateTime e = LocalDateTime.of(2025, 1, 1, 11, 0);

		service.assignKuenstlerToStage(buehne, artist, s, e);

		verify(slotRepo).save(any(StageSlot.class));
	}

	@Test
	void assign_fails_whenStageOverlap() {
		StageSlot existing = new StageSlot(buehne, artist,
			LocalDateTime.of(2025, 1, 1, 10, 0),
			LocalDateTime.of(2025, 1, 1, 11, 0));

		when(slotRepo.findByBuehne(buehne)).thenReturn(Streamable.of(existing));
		when(slotRepo.findByKuenstler(artist)).thenReturn(Streamable.empty()); // <-- REQUIRED

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(
				buehne, artist,
				LocalDateTime.of(2025, 1, 1, 10, 30),
				LocalDateTime.of(2025, 1, 1, 11, 30)
			)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Bühne wird bereits in diesem Slot genutzt!");
	}

	@Test
	void assign_fails_whenArtistBusyOnAnotherStage() {
		StageSlot artistBusy = new StageSlot(mock(Buehne.class), artist,
			LocalDateTime.of(2025, 1, 1, 10, 0),
			LocalDateTime.of(2025, 1, 1, 12, 0));

		when(slotRepo.findByBuehne(buehne)).thenReturn(Streamable.empty());
		when(slotRepo.findByKuenstler(artist)).thenReturn(Streamable.of(artistBusy));

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(
				buehne, artist,
				LocalDateTime.of(2025, 1, 1, 11, 0),
				LocalDateTime.of(2025, 1, 1, 12, 0)
			)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Künstler spielt bereits zu dieser Zeit auf einer anderen Bühne!");
	}

	@Test
	void assign_fails_whenStageIsNull() {
		LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
		LocalDateTime end = LocalDateTime.of(2025, 1, 1, 11, 0);

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(null, artist, start, end)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Bühne darf nicht null sein");
	}

	@Test
	void assign_fails_whenArtistIsNull() {
		LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
		LocalDateTime end = LocalDateTime.of(2025, 1, 1, 11, 0);

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(buehne, null, start, end)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Kuenstler darf nicht null sein!");
	}
	@Test
	void assign_fails_whenStartIsNull() {
		LocalDateTime end = LocalDateTime.of(2025, 1, 1, 11, 0);

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(buehne, artist, null, end)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Start darf nicht null sein!");
	}

	@Test
	void assign_fails_whenEndIsNull() {
		LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);

		assertThatThrownBy(() ->
			service.assignKuenstlerToStage(buehne, artist, start, null)
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Ende darf nicht null sein");
	}

	@Test
	void assign_ok_minimumDurationSlot() {
		when(slotRepo.findByBuehne(any(Buehne.class))).thenReturn(Streamable.empty());
		when(slotRepo.findByKuenstler(any(Kuenstler.class))).thenReturn(Streamable.empty());

		LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
		LocalDateTime end = start.plusMinutes(30);

		service.assignKuenstlerToStage(buehne, artist, start, end);

		verify(slotRepo).save(any(StageSlot.class));
	}


}
