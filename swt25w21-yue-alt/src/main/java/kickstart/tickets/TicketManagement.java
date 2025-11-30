package kickstart.tickets;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Service;

import kickstart.festival.Festival;
import kickstart.festival.FestivalIdentifier;
import kickstart.festival.FestivalRepository;

@Service
public class TicketManagement {

    private final TicketRepository ticketRepository;
    private final SoldTicketRepository soldTicketRepository;
    private final FestivalRepository festivalRepository;
    private final UniqueInventory<UniqueInventoryItem> inventory;

    public TicketManagement(TicketRepository repo,
                            SoldTicketRepository soldRepo,
                            FestivalRepository festivalRepo,
                            UniqueInventory<UniqueInventoryItem> inventory) {
        this.ticketRepository = repo;
        this.soldTicketRepository = soldRepo;
        this.festivalRepository = festivalRepo;
        this.inventory = inventory;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll().stream().toList();
    }

    public List<Ticket> findByCategoryIgnoreCase(String category) {
        return ticketRepository.findByCategoryIgnoreCase(category);
    }

    public void updatePrice(String category, Money price) {
        findByCategoryIgnoreCase(category).forEach(ticket -> {
            ticket.setPrice(price);
            ticketRepository.save(ticket);
        });
    }

    public void updateStock(String category, int stock) {
        findByCategoryIgnoreCase(category).forEach(ticket -> {
            inventory.findByProductIdentifier(ticket.getId()).ifPresent(item -> {
                item.decreaseQuantity(item.getQuantity());
                item.increaseQuantity(Quantity.of(stock));
                inventory.save(item);
            });
        });
    }

    public void printTicket(String ticketId) {
        ticketRepository.findByProductIdentifier(ticketId)
                .ifPresent(ticket ->
                        inventory.findByProductIdentifier(ticket.getId()).ifPresent(item -> {
                            if (!item.getQuantity().isZeroOrNegative()) {
                                item.decreaseQuantity(Quantity.of(1));
                                inventory.save(item);
                                SoldTicket sold = new SoldTicket(ticket);
                                soldTicketRepository.save(sold);
                                sold.printSoldTicket();
                            } else {
                                System.out.println("Stock is empty");
                            }
                        })
                );
    }

    public Quantity getStock(Ticket ticket) {
        return inventory.findByProductIdentifier(ticket.getId())
                .map(UniqueInventoryItem::getQuantity)
                .orElse(Quantity.of(0));
    }

    public Map<String, Quantity> getStockMap() {
        return ticketRepository.findAll().stream()
                .collect(Collectors.toMap(
                        t -> t.getId().toString(),
                        this::getStock
                ));
    }

    public void checkTicket(String soldTicketId) {
        soldTicketRepository.findById(soldTicketId)
                .ifPresentOrElse(
                        SoldTicket::printSoldTicket,
                        () -> System.out.println("Invalid Ticket ID")
                );
    }

    public Map<String, String> getFestivalNames() {
        return ticketRepository.findAll().stream().collect(Collectors.toMap(
                t -> t.getId().toString(),
                t -> festivalRepository.findById(t.getFestivalID())
                        .map(Festival::getName)
                        .orElse("Unknown Festival")
        ));
    }

    public void deleteTicketsByFestival(FestivalIdentifier id) {
        ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getFestivalID().equals(id))
                .forEach(ticket -> {
                    inventory.findByProductIdentifier(ticket.getId()).ifPresent(inventory::delete);
                    ticketRepository.delete(ticket);
                });
    }
}
