package kickstart.catering;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.EURO;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class StorageInitializer {
    
    private final LebensmittelRepository lebensmittelRepository;
    private final UniqueInventory<UniqueInventoryItem> inventory;

    public StorageInitializer(LebensmittelRepository lebensmittelRepository, UniqueInventory<UniqueInventoryItem> inventory) {
        this.lebensmittelRepository = lebensmittelRepository;
        this.inventory = inventory;
    }

    @PostConstruct
    private void initializeLager() {
        if (lebensmittelRepository.findAll().iterator().hasNext()) return;
        Lebensmittel Wasser = new Lebensmittel("Wasser", Money.of(1, EURO));
        lebensmittelRepository.save(Wasser);
        inventory.save(new UniqueInventoryItem(Wasser, Quantity.of(100)));
    }
}
