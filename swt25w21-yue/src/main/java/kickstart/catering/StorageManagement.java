package kickstart.catering;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.EURO;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Service;



@Service
public class StorageManagement {
    private final LebensmittelRepository lebensmittelRepository;
    private final UniqueInventory<UniqueInventoryItem> inventory;
    
    public StorageManagement(LebensmittelRepository lebensmittelRepository, UniqueInventory<UniqueInventoryItem> inventory) {
        this.lebensmittelRepository = lebensmittelRepository;
        this.inventory = inventory;
    }

    public List<Lebensmittel> findAll() {
        return lebensmittelRepository.findAll().stream().toList();
    }

      public Quantity getStock(Lebensmittel lebensmittel) {
        return inventory.findByProductIdentifier(lebensmittel.getId())
                .map(UniqueInventoryItem::getQuantity)
                .orElse(Quantity.of(0));
    }

    public Map<String, Quantity> getStockMap() {
        return lebensmittelRepository.findAll().stream()
                .collect(Collectors.toMap(
                        item -> item.getId().toString(),
                        this::getStock
                ));
    }

    public void Lebensmittelhinzufügen(String lebensmittelart, 
        int lebensmittelanzahl, 
        int lebensmittelpreis ) {           
        if (lebensmittelRepository.findByName(lebensmittelart).isEmpty()) {
        Lebensmittel lebensmittel = new Lebensmittel(lebensmittelart, Money.of(lebensmittelpreis, EURO));
        lebensmittelRepository.save(lebensmittel);
        inventory.save(new UniqueInventoryItem(lebensmittel, Quantity.of(lebensmittelanzahl)));
        }
    }

}
