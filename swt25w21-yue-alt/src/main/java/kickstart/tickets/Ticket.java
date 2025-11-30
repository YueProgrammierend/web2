package kickstart.tickets;

import java.util.List;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import kickstart.festival.FestivalIdentifier;

@Entity
public class Ticket extends Product {

    @Embedded
    private FestivalIdentifier festivalID;

    @SuppressWarnings({ "unused", "deprecation" })
	private Ticket() {}

    public Ticket(String name, Money price, FestivalIdentifier festivalID) {
        super(name, price);
        this.festivalID = festivalID;
    }

    public String getDisplayCategories() {
        List<String> categories = getCategories().stream().collect(Collectors.toList());

        if (categories.contains("day")) {
            return categories.stream()
                    .filter(cat -> !cat.equals("day"))
                    .findFirst()
                    .map(day -> "day ticket: " + day)
                    .orElse("day ticket");
        }
        if (categories.contains("camping")) {
            return "camping ticket";
        }
        return String.join(": ", categories);
    }

    public FestivalIdentifier getFestivalID() {
        return festivalID;
    }
}
