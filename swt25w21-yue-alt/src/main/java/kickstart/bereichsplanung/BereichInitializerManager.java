package kickstart.bereichsplanung;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.salespointframework.core.Currencies.EURO;

@Service
public class BereichInitializerManager {

	private final BereichRepository bereichRepository;

	public BereichInitializerManager(BereichRepository bereichRepository) {
		Assert.notNull(bereichRepository, "BereichRepository must not be null!");
		this.bereichRepository = bereichRepository;
	}

	public void initializePlan1() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",0, 30, 20, 30,false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),0, 0, 40, 30,  false, 400, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),0, 30, 40, 30, false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),0, 60, 20, 20, false, 5, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),0, 80, 20, 10, false,false));
		bereichRepository.save(new Wiese("Wiese1",40, 0, 40, 90, false));
		bereichRepository.save(new Wiese("Wiese2",80, 0, 20, 30, false));
		bereichRepository.save(new Wiese("Wiese3",80, 60, 20, 30, false));
		bereichRepository.save(new Wiese("Wiese4",20, 60, 20, 30, false));

	}

	public void initializePlan2() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",40, 70, 40, 20, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),90, 0, 10, 50, false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),0, 0, 40, 20, false, 17, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),90, 50, 10, 40, false, false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),0, 20, 40, 70, false, 60, false));
		bereichRepository.save(new Wiese("Wiese1",40, 0, 50, 70, false));
		bereichRepository.save(new Wiese("Wiese1",80, 70, 10, 20, false));

	}

	public void initializePlan3() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",0, 20, 20, 30, false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),0, 50, 100, 40, false, 98, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),60, 0, 40, 20, false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),60, 40, 40, 10,  false, 5, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),0, 0, 20, 20, false,false));
		bereichRepository.save(new Wiese("Wiese1",20, 0, 40, 50,false));
		bereichRepository.save(new Wiese("Wiese1",60, 20, 40, 20, false));

	}

	public void initializePlan4() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",40, 0, 20, 10, false));
		bereichRepository.save(new Buehne("Bühne2",80, 40, 20, 30, false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),40, 40, 20, 50, false, 20, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),0, 40, 40, 30, false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),0, 0, 20, 40, false, 5, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),0, 70, 40, 20, false, false));
		bereichRepository.save(new Wiese("Wiese1",20, 10, 60, 30, false));
		bereichRepository.save(new Wiese("Wiese1",60, 40, 20, 50, false));
		bereichRepository.save(new Wiese("Wiese1",80, 70, 20, 20, false));
		bereichRepository.save(new Wiese("Wiese1",20, 0, 20, 10, false));
		bereichRepository.save(new Wiese("Wiese1",60, 0, 20, 10, false));
		bereichRepository.save(new Wiese("Wiese1",80, 0, 20, 40, false));

	}

	public void initializePlan5() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",80, 20, 20, 40, false));
		bereichRepository.save(new Buehne("Bühne2",0, 20, 20, 40, false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),0, 60, 45, 30, false, 800, false));
		bereichRepository.save(new Camping("Camping2", Money.of(90,EURO),55, 60, 45, 30,  false, 804, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),45, 20, 10, 40, false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),0, 0, 100, 10, false, 5, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),45, 60, 10, 30, false, false));
		bereichRepository.save(new Wiese("Wiese1",0, 10, 100, 10, false));
		bereichRepository.save(new Wiese("Wiese1",55, 20, 25, 40, false));
		bereichRepository.save(new Wiese("Wiese1",20, 20, 25, 40, false));

	}

	public void initializePlan6() {
		bereichRepository.deleteAll();

		bereichRepository.save(new Buehne("Bühne1",250, 100, 50, 100, false));
		bereichRepository.save(new Buehne("Bühne2",100, 0, 100, 50, false));
		bereichRepository.save(new Buehne("Bühne3",100, 50, 100,0, false));
		bereichRepository.save(new Camping("Camping1", Money.of(90,EURO),50, 200, 200, 50,  false, null, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),0, 0, 100, 100, false, false));
		bereichRepository.save(new Catering("Catering1",Money.of(90,EURO),200, 0, 100, 100,  false, false));
		bereichRepository.save(new Toiletten("Toiletten1",Money.of(90,EURO),0, 200, 50, 50, false, 5, false));
		bereichRepository.save(new ErsteHilfeZelt("Erste-Hilfe Zelt",Money.of(90,EURO),250, 200, 50, 100, false, false));
		bereichRepository.save(new Wiese("Wiese1",50, 100, 200, 100, false));
		bereichRepository.save(new Wiese("Wiese1",00, 50, 100, 50, false));

	}

	public void deleteAllData() {
		bereichRepository.deleteAll();
	}

}