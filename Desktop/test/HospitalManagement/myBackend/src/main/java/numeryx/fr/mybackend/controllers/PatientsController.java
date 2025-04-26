package numeryx.fr.mybackend.controllers;


import numeryx.fr.mybackend.entities.Patient;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.DoctorPatientService;
import numeryx.fr.mybackend.services.PatientsServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="http://localhost:4200")
public class PatientsController{
	
	@Autowired
	private PatientsServices services;
	
	@Autowired
	private DoctorPatientService servicesDoctorPatients;


	@GetMapping("controller/patients")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> index() {
		return ResponseEntity.ok(services.findAll());
	}
	
	@GetMapping("controller/patients/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(services.findById(id));
	}

	@Transactional
	@PostMapping("controller/patients")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> save(@RequestBody Patient patient) {
		return ResponseEntity.ok(services.save(patient));
	}

	@Transactional
	@PutMapping("controller/patients/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody Patient patient, @PathVariable Long id) {
		Patient patientBdd = services.findById(id);
		patientBdd.setSymptoms(patient.getSymptoms());
		patientBdd.setPerson(patient.getPerson());
		return ResponseEntity.ok(services.save(patientBdd));
	}

	@Transactional
	@DeleteMapping("controller/patients/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.ok(new MessageResponse("Patient deleted"));
	}

	@Transactional
	@PostMapping("controller/patients/{idP}/assign/{idD}")
	public ResponseEntity<?> assignDoctor(@PathVariable Long idP, @PathVariable Long idD) throws Exception {
		servicesDoctorPatients.assignRelationShip(idP, idD);
		return ResponseEntity.ok(new MessageResponse("Patient added under doctor successfully."));
	}
	 
	@GetMapping("controller/patients/{id}/doctor")
	public ResponseEntity<?> getPatientDoctor(@PathVariable Long id) {
		Patient patient = services.findById(id);
		return ResponseEntity.ok(patient.getDoctor());
	}
	
	@GetMapping("controller/patients/email/{email}")
	public ResponseEntity<?> getPatientByEmail(@PathVariable String email) {
		Patient patient = services.findPatientByPersonEmail(email);
		if(patient != null) {
			return ResponseEntity.ok(patient);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("No patient found with email"));
	}
	
}
