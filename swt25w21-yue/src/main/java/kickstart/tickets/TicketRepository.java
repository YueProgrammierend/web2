package kickstart.tickets;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.salespointframework.catalog.Catalog;

public interface TicketRepository extends Catalog<Ticket> {

    default List<Ticket> findByCategoryIgnoreCase(String category) {
        if (category == null) {
            return List.of();
        }

        return findAll().stream()
                .filter(ticket -> ticket.getCategories().stream()
                        .anyMatch(cat -> cat != null && cat.equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }

    default Optional<Ticket> findByProductIdentifier(String id) {
        if (id == null) {
            return Optional.empty();
        }

        return findAll().stream()
                .filter(ticket -> ticket.getId() != null && ticket.getId().toString().equals(id))
                .findFirst();
    }
}
