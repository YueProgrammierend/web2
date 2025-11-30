package kickstart.bereichsplanung;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.javamoney.moneta.Money;

@Entity
@DiscriminatorValue("Erste-Hilfe Zelt")
public class ErsteHilfeZelt extends Bereich {
	private boolean rented;

	protected ErsteHilfeZelt() {}

	public ErsteHilfeZelt(String name, Money price,Integer positionX, Integer positionY, Integer width, Integer height,boolean rented, boolean blocked) {
		super(name, price,positionX,positionY,width,height,blocked);
		this.rented = rented;
	}

	public boolean getRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}
}
