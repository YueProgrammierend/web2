package kickstart.bereichsplanung;

import org.salespointframework.catalog.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import javax.money.NumberValue;

@Service
@Transactional
public class BereichManager {

	private final BereichRepository bereichRepository;

	public BereichManager(BereichRepository bereichRepository) {
		this.bereichRepository = bereichRepository;
	}

	public Bereich save(Bereich bereich) {
		return bereichRepository.save(bereich);
	}

	public List<Bereich> findAll() {
		return bereichRepository.findAll().toList();
	}

	public Optional<Bereich> findById(String id) {
		Product.ProductIdentifier productId = Product.ProductIdentifier.of(id);
		return bereichRepository.findById(productId);
	}

	public void deleteById(String id) {
		Product.ProductIdentifier productId = Product.ProductIdentifier.of(id);
		bereichRepository.deleteById(productId);
	}

	public void deleteAll() {
		bereichRepository.deleteAll();
	}



	public double calculateTotalRentalCost() {
		return bereichRepository.findAll().stream()
			.mapToDouble(bereich -> {
				if (bereich instanceof Camping camping) {
					return camping.getPrice().getNumber().doubleValue();
				} else if (bereich instanceof Catering catering) {
					return catering.getPrice().getNumber().doubleValue();
				} else if (bereich instanceof Toiletten toiletten) {
					return toiletten.getPrice().getNumber().doubleValue();
				}
				return 0.0;
			})
			.sum();
	}
}

