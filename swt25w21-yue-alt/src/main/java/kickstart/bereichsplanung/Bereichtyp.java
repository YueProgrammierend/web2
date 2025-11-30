package kickstart.bereichsplanung;

public enum Bereichtyp {
	TOILETTE("Toilette"),
	BUEHNE("Bühne"),
	CAMPING("Camping"),
	CATERING("Catering"),
	WIESE("Wiese"),
	ERSTEHILFEZELT("Erstehilfe-Zelt");

	private final String displayName;

	Bereichtyp(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
