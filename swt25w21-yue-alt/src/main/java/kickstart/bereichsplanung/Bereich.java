package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typ")
public abstract class Bereich extends Product {

	private Integer positionX;
	private Integer positionY;
	private Integer width;
	private Integer height;
	private boolean blocked;

	protected Bereich() {}


	public Bereich(String name, Money price, Integer positionX, Integer positionY, Integer width, Integer height, boolean blocked) {
		super(name, price);
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.blocked = blocked;
	}

	public Integer getPositionX() {
		return positionX;
	}

	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}

	public Integer getPositionY() {
		return positionY;
	}

	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer hoehe) {
		this.height = hoehe;
	}

	public boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}
