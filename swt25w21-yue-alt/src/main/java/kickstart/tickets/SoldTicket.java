package kickstart.tickets;

import java.time.LocalDateTime;
import java.util.UUID;

import org.javamoney.moneta.Money;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SoldTicket {

    @Id
    private String id;

    private String name;
    private Money price;
    private String type;
    private LocalDateTime soldAt;

    @SuppressWarnings("unused")
    private SoldTicket() {}

    public SoldTicket(Ticket ticket) {
        this.id = UUID.randomUUID().toString();
        this.name = ticket.getName();
        this.price = Money.from(ticket.getPrice());
        this.type = ticket.getDisplayCategories();
        this.soldAt = LocalDateTime.now();
    }

    public String getSoldTicketId() { return id; }
    public String getName() { return name; }
    public Money getPrice() { return price; }
    public String getType() { return type; }
    public LocalDateTime getSoldAt() { return soldAt; }

    public void printSoldTicket() {
        System.out.println("=== Printing Ticket ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Type: " + type);
        System.out.println("Sold At: " + soldAt);
        System.out.println("====================");
    }
}
