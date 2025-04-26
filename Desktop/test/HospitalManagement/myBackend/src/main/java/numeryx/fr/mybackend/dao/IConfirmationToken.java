package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.ConfirmationToken;
import numeryx.fr.mybackend.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IConfirmationToken extends CrudRepository<ConfirmationToken, Long> {

	ConfirmationToken findByConfirmationToken(String token);
	List<ConfirmationToken> findByUser(User user);
	
}
