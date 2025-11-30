package kickstart.bereichsplanung;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.javamoney.moneta.Money;

import static org.salespointframework.core.Currencies.EURO;

@Entity
@DiscriminatorValue("Wiese")
public class Wiese extends Bereich {

	protected Wiese() {}

	public Wiese( String name,Integer positionX, Integer positionY, Integer width, Integer height, boolean blocked ) {
		super(name, Money.of(0,EURO),positionX,positionY,width,height, blocked);
	}
}
