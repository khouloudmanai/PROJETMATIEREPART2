package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.entities.Person;
import numeryx.fr.mybackend.payload.request.FindPersonRegisterRequest;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterFindController {

	@Autowired
	private PersonServices servicesPerson;

	@PostMapping("/auth/register/person")
	public ResponseEntity<?> findByNameAndLastNameAndDob(@RequestBody FindPersonRegisterRequest request) {
		Person person = servicesPerson.findByNameAndLastNameAndDob(request.getFirstName(), request.getLastName(),
				request.getDob());
		if (person != null) {
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("No records found!"));
	}
}
