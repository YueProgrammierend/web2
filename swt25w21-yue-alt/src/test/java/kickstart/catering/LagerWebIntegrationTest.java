package kickstart.catering;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LagerWebIntegrationTest {

    @Autowired MockMvc mvc;

    @Test
    void lagerPageLoadsSuccessfully() throws Exception {
        mvc.perform(get("/lager"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lager"))
                .andExpect(model().attribute("lager", is(not(emptyIterable()))))
                .andExpect(view().name("lager"));
    }

    @Test
    void lebensmittelhinzufügenRouteRedirects() throws Exception {
        mvc.perform(post("/lager/lebensmittelhinzufügen")
                .param("Lebensmittelart","test")
                .param("Lebensmittelanzahl", "1")
                .param("Lebensmittelpreis", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lager"));
    }
}