package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Person;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPersonDao extends CrudRepository<Person, Long>{

	Optional<Person> findByEmail(String email);
	List<Person> findByEnableTrue();
	Person findByNameAndLastNameAndDob(String name, String lastName, LocalDate localDate);
}
