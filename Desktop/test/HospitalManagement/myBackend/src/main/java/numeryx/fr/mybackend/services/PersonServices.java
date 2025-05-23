package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IPersonDao;
import numeryx.fr.mybackend.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class PersonServices {

	@Autowired
	private IPersonDao personDao;
	
	@Transactional(readOnly = true)
	public List<Person> findAll() {
		return (List<Person>) personDao.findByEnableTrue();
	}
	
	@Transactional(readOnly = true)
	public Person findById(Long id) {
		return personDao.findById(id).orElse(null);
	}
	
	@Transactional
	public Person save(Person person) {
		return personDao.save(person);
	}

	@Transactional
	public void delete(Long id) {
		Person person = findById(id);
		person.setEnable(false);
		person.setStatus(false);
	}
	
	@Transactional(readOnly = true)
	public Person findByEmail(String email) throws Exception{
		return personDao.findByEmail(email).orElseThrow(() -> new Exception("Person email: " + email));
	}
	
	@Transactional(readOnly = true)
	public Person findByNameAndLastNameAndDob(String name, String lastName, LocalDate dob) {
		return personDao.findByNameAndLastNameAndDob(name, lastName, dob);
	}
}
