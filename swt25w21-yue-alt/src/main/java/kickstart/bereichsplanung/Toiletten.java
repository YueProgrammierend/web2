package kickstart.bereichsplanung;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.javamoney.moneta.Money;

@Entity
@DiscriminatorValue("Toiletten")
public class Toiletten extends Bereich {
	private boolean rented;
	private Integer stallCount;

	protected Toiletten() {}

	public Toiletten(String name, Money price, Integer positionX, Integer positionY, Integer width, Integer height, boolean rented, Integer stallCount, boolean blocked) {
		super(name,price,positionX,positionY,width,height, blocked);
		this.rented = rented;
		this.stallCount = stallCount;
	}

	public boolean getRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public Integer getStallCount() {
		return stallCount;
	}

	public void setStallCount(Integer stallCount) {
		this.stallCount = stallCount;
	}
}
