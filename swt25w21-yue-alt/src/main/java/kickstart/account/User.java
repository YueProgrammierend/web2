package kickstart.account;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import kickstart.festival.Festival;

import org.salespointframework.useraccount.UserAccount;


// @Table (name = "tb_user")
@Entity
public class User {

	@EmbeddedId
	private UserIdentifier id;

	@OneToOne
	@MapsId
	private UserAccount userAccount;
	// UserAccount macht Username, Passwort, anmelden, Löschen, Sperren
	// Alles andere muss in Employeemanagement (UserManagement)

	@OneToOne(optional = true)
/*	 @Nullable               // Yue: mit `Nullable` implementiert nicht erfolgreich
	 Wir können es zunächst als OneToOne umsetzen, dann eine Methodenvalidierung verwenden.*/
	private Festival festival; // Haakon: Manager oder Planer = null

	public User() {}

	public User(/*String name,*/ UserAccount userAccount, Festival festival) {
		this.id = new UserIdentifier(userAccount);
		//this.name = name;
		this.userAccount = userAccount;
		this.festival = festival;
	}


	public UserIdentifier getId() {
		return id;
	}
/* 
	public String getName() {
		return name;
	}
*/
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
	}
}
