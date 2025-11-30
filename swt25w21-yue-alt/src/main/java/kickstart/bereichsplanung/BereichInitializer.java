package kickstart.bereichsplanung;

import org.salespointframework.core.DataInitializer;
import org.springframework.stereotype.Component;

@Component
public class BereichInitializer implements DataInitializer {
	private final BereichInitializerManager bereichInitializerManager;

	public BereichInitializer(BereichInitializerManager bereichInitializerManager) {
		this.bereichInitializerManager= bereichInitializerManager;
	}


	@Override
	public void initialize() {

	}

}

