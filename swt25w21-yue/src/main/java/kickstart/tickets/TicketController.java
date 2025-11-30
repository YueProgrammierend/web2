package kickstart.tickets;

import java.util.Map;
import java.util.Optional;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.EURO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    private final TicketManagement ticketManagement;
    private final SoldTicketRepository soldTicketRepository;

    public TicketController(TicketManagement ticketManagement, SoldTicketRepository soldRepo) {
        this.ticketManagement = ticketManagement;
        this.soldTicketRepository = soldRepo;
    }

    @GetMapping("/tickets")
    public String tickets(
            Model model,
            @RequestParam(required = false) String editTicketId,
            @RequestParam(required = false) String editField
    ) {
        var tickets = ticketManagement.findAll();
        Map<String, ?> stockMap = ticketManagement.getStockMap();

        model.addAttribute("festivalNames", ticketManagement.getFestivalNames());
        model.addAttribute("tickets", tickets);
        model.addAttribute("stockMap", stockMap);
        model.addAttribute("editTicketId", editTicketId);
        model.addAttribute("editField", editField);

        return "tickets";
    }

    @PostMapping("/tickets/{category}/price")
    public String updatePrice(@PathVariable String category, @RequestParam double price) {
        Money newPrice = Money.of(price, EURO);
        ticketManagement.updatePrice(category, newPrice);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/{category}/stock")
    public String updateStock(@PathVariable String category, @RequestParam int stock) {
        ticketManagement.updateStock(category, stock);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/{ticketId}/print")
    public String printTicket(@PathVariable String ticketId) {
        ticketManagement.printTicket(ticketId);
        return "redirect:/tickets";
    }

    @GetMapping("/ticketvalidation")
    public String ticketvalidation () {
		return "ticketvalidation";
	}

    @PostMapping("/ticketvalidation/check")
    public String checkTicket(@RequestParam String soldTicketId, Model model) {
        Optional<SoldTicket> soldTicket = soldTicketRepository.findById(soldTicketId);

        if (soldTicket.isPresent()) {
            model.addAttribute("validationMessage", "Ticket existiert");
            model.addAttribute("validTicket", true);
            model.addAttribute("validatedTicket", soldTicket.get());
        } else {
            model.addAttribute("validationMessage", "Ticket-ID existiert nicht");
            model.addAttribute("validTicket", false);
            model.addAttribute("validatedTicket", null);
        }

        model.addAttribute("tickets", ticketManagement.findAll());
        model.addAttribute("stockMap", ticketManagement.getStockMap());

        return "ticketvalidation";
    }

}
