package kickstart.account;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.salespointframework.useraccount.*;

public interface UserRepository extends CrudRepository<User, UserIdentifier> {

	@NotNull
	@Override
	Iterable<User> findAll();


}
