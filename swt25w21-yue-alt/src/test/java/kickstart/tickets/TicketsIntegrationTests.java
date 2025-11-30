package kickstart.tickets;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;

@SpringBootTest
class TicketsIntegrationTests {

    @Autowired TicketController controller;

    /**
     * Integration test for TicketController directly.
     */
    @Test
    void ticketsPagePopulatesModel() {
        var model = new ExtendedModelMap();

        String viewName = controller.tickets(model, null, null);

        assertThat(viewName).isEqualTo("tickets");
        assertThat(model.containsAttribute("tickets")).isTrue();
        assertThat(model.containsAttribute("stockMap")).isTrue();
        assertThat(model.containsAttribute("festivalNames")).isTrue();

        List<?> tickets = (List<?>) model.asMap().get("tickets");
        assertThat(tickets).isNotNull();

        /**
         * Checks every ticket has a festival name
         */
        var festivalNames = (java.util.Map<String, String>) model.asMap().get("festivalNames");
        for (Object t : tickets) {
            Ticket ticket = (Ticket) t;
            assertThat(festivalNames).containsKey(ticket.getId().toString());
        }
    }
}
