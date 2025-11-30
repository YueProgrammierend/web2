package kickstart.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Web integration tests for the TicketController.
 * Simulates HTTP requests to ensure that all endpoints respond correctly.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TicketsWebIntegrationTests {

    @Autowired MockMvc mvc;

    /**
	 * Sample integration test using fake HTTP requests to the system and using the expectations API to define
	 * constraints.
	 */
    @Test
    void ticketsPageLoadsSuccessfully() throws Exception {
        mvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tickets"))
                .andExpect(model().attributeExists("festivalNames"))
                .andExpect(view().name("tickets"));
    }


    /**
     * Checks that updating the price of a ticket category redirects back to /tickets
     */
    @Test
    void updatePriceRouteRedirects() throws Exception {
        mvc.perform(post("/tickets/day/price")
                .param("price", "69"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tickets"));
    }

    /**
     * Checks that updating the stock of a ticket category redirects back to /tickets
     */
    @Test
    void updateStockRouteRedirects() throws Exception {
        mvc.perform(post("/tickets/day/stock")
                .param("stock", "99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tickets"));
    }

    /**
     * Checks that triggering print of a ticket redirects back to /tickets
     */
    @Test
    void printTicketRouteRedirects() throws Exception {
        mvc.perform(post("/tickets/1/print"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tickets"));
    }
}
