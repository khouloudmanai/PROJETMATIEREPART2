package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.entities.Person;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

	@Autowired
	private PersonServices service;


	@GetMapping("controller/person")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	private ResponseEntity<?> index() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("controller/person/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	private ResponseEntity<?> showId(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping("controller/person")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> save(@RequestBody Person person) {
		return ResponseEntity.ok(service.save(person));
	}

	@PutMapping("controller/person/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> edit(@RequestBody Person person, @PathVariable Long id) {
		Person personBdd = service.findById(id);
		personBdd.setName(person.getName());
		personBdd.setLastName(person.getLastName());
		personBdd.setEmail(person.getEmail());
		personBdd.setPhoneNumber(person.getPhoneNumber());
		personBdd.setHomeNumber(person.getHomeNumber());
		personBdd.setAge(person.getAge());
		personBdd.setCity(person.getCity());
		personBdd.setDob(person.getDob());
		personBdd.setSocialSecNumber(person.getSocialSecNumber());
		
		return ResponseEntity.ok(service.save(personBdd));
	}

	@DeleteMapping("controller/person/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	private ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Person deleted");
	}
	
	@GetMapping("controller/person/e/{email}")
	private ResponseEntity<?> findByEmail(@PathVariable String email) throws Exception {
		Person person = service.findByEmail(email);
		if(person != null) {
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("No person found with email"));
	}
}
