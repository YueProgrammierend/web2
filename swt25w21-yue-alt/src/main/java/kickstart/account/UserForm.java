package kickstart.account;

//Erstellung von Usern zu neuen Festivals 
//nur von Manager, implemantierung im Management
import kickstart.festival.Festival;
import org.springframework.validation.Errors;

public class UserForm {
	private UserIdentifier id;
	private Festival festival;
	private Festival originalFestival;

	UserForm(){}

	public UserForm(UserIdentifier id, Festival festival) {
		this.id = id;
		this.festival = festival;
		this.originalFestival = festival;
	}

	public UserIdentifier getId(){
		return this.id;
	}

	public Festival getFestival(){
		return this.festival;
	}
	public void setId(UserIdentifier id){
		this.id = id;
	}

	public void setFestival(Festival festival){
		this.festival = festival;
	}

	public void validate(UserRepository users,Errors errors) {
		User user = users.findById(id)
			.orElseThrow(() -> new IllegalStateException("User nicht gefunden"));
		if (user.getUserAccount().hasRole(UserRole.MANAGER) ||
			user.getUserAccount().hasRole(UserRole.PLANER)) {
			if (festival != null) {
				errors.rejectValue(
					"festival", "",
					"Manager und Veranstalter haben Zugriff auf alle Festivals " +
						"und dürfen nicht an ein bestimmtes Festival gebunden werden.");
			}

			return;
		}

		return;
	}
}


