package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserDao extends JpaRepository<User, Long> {

	  User findByEmail(String email);

	  Optional<User> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);

	  List<User> findByEnableTrue();
	  List<User> findByStatusTrue();

}
