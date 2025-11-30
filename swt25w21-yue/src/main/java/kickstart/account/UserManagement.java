package kickstart.account;

import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;
import static kickstart.account.UserRole.*;

@Service
@Transactional
public class UserManagement{

	private final UserRepository users;
	private final UserAccountManagement userAccounts;

	UserManagement(UserRepository users, UserAccountManagement userAccounts) {
		Assert.notNull(users, "UserAccountRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManagement must not be null!");
		this.users = users;
		this.userAccounts = userAccounts;
	}

	//darf nur Manager und Planer
	public void createUser(RegistrationForm form) {

		Assert.notNull(form, "Registration form must not be null!");

		var password = UnencryptedPassword.of(form.getPassword());
		var userAccount = userAccounts.create(form.getName(), password, EMPLOYEE);
	}

	// public Streamable<User> findAll() {
	// 	return users.findAll();
	// }

	public Iterable<User> findAll() {
		return users.findAll();
	}
}
