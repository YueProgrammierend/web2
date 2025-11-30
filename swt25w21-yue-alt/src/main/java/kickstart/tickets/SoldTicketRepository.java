package kickstart.tickets;

import org.springframework.data.repository.CrudRepository;

public interface SoldTicketRepository extends CrudRepository<SoldTicket, String> {

    // @Override
    // Optional<SoldTicket> findById(String id);

}
