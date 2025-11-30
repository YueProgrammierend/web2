package kickstart.account;

import jakarta.persistence.Embeddable;
import org.jmolecules.ddd.types.Identifier;
import org.salespointframework.useraccount.UserAccount;

import java.util.Objects;
import java.util.UUID;

//Vanessa's Identifier von Festival übernehemen

@Embeddable
public class UserIdentifier implements Identifier {
	private UUID userAccountId;

	public UserIdentifier() {}

	public UserIdentifier(UserAccount userAccount) {
		this.userAccountId = UUID.fromString(userAccount.getId().toString());
	}
	//
	//	public UserIdentifier(UUID userAccountID) {
	//		this.userAccountID = userAccountID;
	//	}
	//

	public UUID getId() {
		return userAccountId;
	}

}
