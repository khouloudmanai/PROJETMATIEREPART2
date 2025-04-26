package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.entities.ConfirmationToken;
import numeryx.fr.mybackend.entities.Person;
import numeryx.fr.mybackend.entities.User;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.ConfirmationTokenService;
import numeryx.fr.mybackend.services.PersonServices;
import numeryx.fr.mybackend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	Logger logger = Logger.getLogger(AuthController.class.getName());
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserServices services;

	@Autowired
	private PersonServices servicesPerson;

	@Autowired
	private ConfirmationTokenService IconfirmationToken;

	@GetMapping("controller/users")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(services.findAll());
	}

	@GetMapping("controller/users/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(services.findById(id));
	}

	@GetMapping("controller/users/find/{username}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> findByUsername(@PathVariable String username) {
		return ResponseEntity.ok(services.findUserByUsername(username));
	}

	@PostMapping("controller/users")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> save(@RequestBody User user) {
		return ResponseEntity.ok(services.save(user));
	}

	@PutMapping("controller/users/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> saveEdit(@RequestBody User user, @PathVariable Long id) {
		User userDbb = services.findById(id);
		userDbb.setUsername(user.getUsername());
		userDbb.setPassword(passwordEncoder.encode(user.getPassword()));
		userDbb.setEmail(user.getEmail());
		userDbb.setRoles(user.getRoles());
		userDbb.setImage(user.getImage());
		userDbb.setPerson(user.getPerson());
		return ResponseEntity.ok(services.save(userDbb));
	}

	@DeleteMapping("controller/users/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
		User user = services.findById(id);
		Person person = servicesPerson.findByEmail(user.getEmail());
		ConfirmationToken token = IconfirmationToken.findByUser(user).get(0);
		services.delete(id);
		IconfirmationToken.delete(token);
		servicesPerson.delete(person.getId());
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User deleted"));
	}
}
