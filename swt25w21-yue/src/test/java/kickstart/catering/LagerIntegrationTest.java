package kickstart.catering;

import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;


@SpringBootTest
class LagerIntegrationTest {
    @Autowired LagerController controller;
	@Test

	void lagerPagePopulatesModel() {
		Model model = new ExtendedModelMap();
		String viewname = controller.lager(model);
		assertThat(viewname).isEqualTo("lager");
		assertThat(model.containsAttribute("lager")).isTrue();
        assertThat(model.containsAttribute("stockMap")).isTrue();

		List<?> lager = (List<?>) model.asMap().get("lager");
        assertThat(lager).isNotEmpty();
	} 
}

	