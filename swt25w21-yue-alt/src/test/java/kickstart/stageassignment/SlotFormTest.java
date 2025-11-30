package kickstart.stageassignment;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class SlotFormTest {

	@Test
	void validInterval_shouldReturnTrue() {
		SlotForm form = new SlotForm();
		form.setStart(LocalDateTime.of(2025, 1, 1, 10, 0));
		form.setEnd(LocalDateTime.of(2025, 1, 1, 11, 0));

		assertThat(form.isValidInterval()).isTrue();
	}

	@Test
	void validInterval_shouldReturnFalse_whenEndBeforeStart() {
		SlotForm form = new SlotForm();
		form.setStart(LocalDateTime.of(2025, 1, 1, 11, 0));
		form.setEnd(LocalDateTime.of(2025, 1, 1, 10, 0));

		assertThat(form.isValidInterval()).isFalse();
	}

	@Test
	void minimumLength_shouldReturnTrue() {
		SlotForm form = new SlotForm();
		form.setStart(LocalDateTime.of(2025, 1, 1, 10, 0));
		form.setEnd(LocalDateTime.of(2025, 1, 1, 11, 0));

		assertThat(form.isMinimumLength(30)).isTrue();
	}

	@Test
	void minimumLength_shouldReturnFalse_whenTooShort() {
		SlotForm form = new SlotForm();
		form.setStart(LocalDateTime.of(2025, 1, 1, 10, 0));
		form.setEnd(LocalDateTime.of(2025, 1, 1, 10, 10));

		assertThat(form.isMinimumLength(30)).isFalse();
	}

	@Test
	void validationMethods_returnFalseWhenNull() {
		SlotForm form = new SlotForm();
		assertThat(form.isValidInterval()).isFalse();
		assertThat(form.isMinimumLength(30)).isFalse();
	}
}
