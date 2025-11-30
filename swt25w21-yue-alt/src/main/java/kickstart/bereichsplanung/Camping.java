package kickstart.bereichsplanung;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.javamoney.moneta.Money;

@Entity
@DiscriminatorValue("Camping")
public class Camping extends Bereich{
	private boolean rented;
	private Integer pitchCount;

	protected Camping(){}

	public Camping(String name, Money price, Integer positionX, Integer positionY, Integer width, Integer height, boolean rented, Integer pitchCount, boolean blocked) {
		super(name, price,positionX,positionY,width,height,blocked);
		this.rented = rented;
		this.pitchCount = pitchCount;
	}

	public Integer getPitchCount() {
		return pitchCount;
	}

	public void setPitchCount(Integer pitchCount) {
		this.pitchCount = pitchCount;
	}

	public boolean getRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

}
