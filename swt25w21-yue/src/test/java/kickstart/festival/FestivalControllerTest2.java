package kickstart.festival;

import kickstart.location.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class FestivalControllerTest2 {
	@Autowired
	FestivalController controller;
	@Autowired
	FestivalRepository festivalRepo;

	@Autowired
	LocationRepository locationRepo;

	@BeforeEach
	@Transactional
	public void setup() {
		 festivalRepo.deleteAll();
		 locationRepo.deleteAll();
	}

	@Test
	public void getFestival() {}
}
