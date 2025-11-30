package kickstart.catering;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import jakarta.persistence.Entity;

@Entity
public class Lebensmittel extends Product {
    @SuppressWarnings({ "unused", "deprecation" })
	private Lebensmittel() {}

    public Lebensmittel(String name, Money price) {
        super(name, price);
    }
}
