package kickstart.account;

import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.UserAccountManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

// import org.salespointframework.useraccount.Role;

@Component
@Order(10)
class UserDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(UserDataInitializer.class);

	private final UserAccountManagement userAccountManagement;
	private final UserManagement userManagement;


	UserDataInitializer(UserAccountManagement userAccountManagement, UserManagement userManagement) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		//Assert.notNull(benutzerManagement, "BenutzerRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.userManagement = userManagement;
	}


	@Override
	public void initialize() {

		if (userAccountManagement.findByUsername("Admin").isPresent()
		&& userAccountManagement.findByUsername("Adrian Scholze").isPresent()) {
			return;
		}

		userAccountManagement.create(
			"Admin",
			UnencryptedPassword.of("123"),
			UserRole.PLANER
		);

		userAccountManagement.create(
			"Adrian Scholze",
			UnencryptedPassword.of("123"),
			UserRole.MANAGER
		);

		userAccountManagement.create(
			"Fl",
			UnencryptedPassword.of("123"),
			UserRole.FESTIVAL_LEADER
		);

	}
}
