package kickstart.tickets;

import java.time.LocalDate;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.EURO;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Component;

import kickstart.festival.Festival;
import kickstart.festival.FestivalIdentifier;

@Component
public class TicketInitializer {

    private final TicketRepository ticketRepository;
    private final UniqueInventory<UniqueInventoryItem> inventory;

    public TicketInitializer(TicketRepository ticketRepository, UniqueInventory<UniqueInventoryItem> inventory) {
        this.ticketRepository = ticketRepository;
        this.inventory = inventory;
    }

    private void createFestivalTicket(String name,
                                       Money price,
                                       FestivalIdentifier festivalID,
                                       LocalDate day,
                                       String category) {

        Ticket ticket = new Ticket(name, price, festivalID);
        ticket.addCategory(category);

        if (day != null) {
            ticket.addCategory(day.toString());
        }
        
        ticketRepository.save(ticket);
        inventory.save(new UniqueInventoryItem(ticket, Quantity.of(50)));
    }

    public void createTickets(Festival festival) {

        LocalDate startDay = festival.getStartDate().toLocalDate();
        LocalDate endDay = festival.getEndDate().toLocalDate();
        LocalDate currDay = startDay;

        while (!currDay.isAfter(endDay)) {
            createFestivalTicket(
                festival.getName() + " - Day Ticket: " + currDay.toString(),
                Money.of(25, EURO),
                festival.getId(),
                currDay,
                "day"
            );

            currDay = currDay.plusDays(1);
        }

        createFestivalTicket(
            festival.getName() + " - Camping Ticket",
            Money.of(25, EURO),
            festival.getId(),
            null,
            "camping"
        );
    }
}
