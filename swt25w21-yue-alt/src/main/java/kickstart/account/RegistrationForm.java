package kickstart.account;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.validation.Errors;


public class RegistrationForm {

	private final @NotEmpty String username, password;
	private final @Nullable String mailAddress;

	public RegistrationForm(String name, String password, @Nullable String mailAddress) {

		this.username = name;
		this.password = password;
		this.mailAddress = mailAddress;
	}

	public String getName() {
		return username;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void validate(Errors errors) {
		// Complex validation goes here

		// Die Passworteinstellung erfordert Groß- und Kleinbuchstaben usw.; Sicherheitsprüfung (später hinzufügen).
	}


}
