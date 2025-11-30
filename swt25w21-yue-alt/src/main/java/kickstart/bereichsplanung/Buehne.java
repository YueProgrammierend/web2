package kickstart.bereichsplanung;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kickstart.stageassignment.StageSlot;
import org.javamoney.moneta.Money;
import java.util.List;

import java.util.ArrayList;

import static org.salespointframework.core.Currencies.EURO;

@Entity
@DiscriminatorValue("Buehne")
public class Buehne extends Bereich{

	@OneToMany(mappedBy = "buehne", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StageSlot> slots = new ArrayList<>();

	protected Buehne() {}

	public Buehne(String name, Integer positionX, Integer positionY, Integer width, Integer height, boolean blocked) {
		super(name,Money.of(0,EURO),positionX,positionY,width,height,blocked);
	}

	public Buehne(String mainStage) {
	}
}
