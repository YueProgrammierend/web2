package kickstart.account;
import org.salespointframework.useraccount.Role;
// import kickstart.staffmanagement.personal.PersonalTyp;

public class UserRole {

	/*
	Userrolen:
	Admin
	Manager
	Planer
	Festivalleiter
	Kartenverkäufer
	Sicherheitspersonal
	Cateringpersonal
	*/
	private UserRole() {}


	public static final Role MANAGER = Role.of("Manager");                  // UC0520 – Einloggen
	public static final Role PLANER = Role.of("Veranstalter");              // Planer
	// public static final Role ORAGANIZAR = Role.of("Veranstalter");             //
	//	public static final Role ADMIN = Role.of("Admin");
	public static final Role FESTIVAL_LEADER = Role.of("Festivalleiter");   // Festivalleiter
	public static final Role EMPLOYEE = Role.of("Mitarbeiter");


	// public static final Role CUSTOMER = Role.of("Kunde");

	//PersonalTyp-Enum
	//PersonalTyp.CATERING.name()
	// public static final Role CATERING = Role.of("Cateringpersonal");           // Cateringpersonal
	//PersonalTyp.VERKAUF.name()
	// public static final Role SALE = Role.of("Verkaufsmitarbeiter");            // Verkaufsmitarbeiter
	// public static final Role SECURITY = Role.of(PersonalTyp.SECURITY.name());  // Sicherheitspersonal
	// public static final Role TECHNIKER = Role.of(PersonalTyp.TECHNIKER.name());
}