package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.entities.Appointment;
import numeryx.fr.mybackend.entities.enums.EStatusAppointment;
import numeryx.fr.mybackend.payload.request.AppointmentRequest;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.AppointmentService;
import numeryx.fr.mybackend.services.DoctorServices;
import numeryx.fr.mybackend.services.PatientsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DoctorServices doctorServices;

	@Autowired
	private PatientsServices patientsServices;

	@PostMapping("/controller/appointment")
	public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest appointment, Errors errors)
			throws Exception {
		Appointment bookedAppointment = new Appointment();
		bookedAppointment.setReason(appointment.getReason());
		bookedAppointment.setPatient(patientsServices.findPatientByPersonEmail(appointment.getEmailPatient()));
		bookedAppointment.setDoctor(doctorServices.findById(appointment.getIdDoctor()));
		bookedAppointment.setDate(appointment.getDate());
		bookedAppointment.setStartTime(appointment.getStartTime());
		bookedAppointment.setEndTime(appointment.getStartTime().plusMinutes(30));
		LocalDate todayDate = LocalDate.now();
		if(bookedAppointment.getPatient() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email not associated with company"));
		}
		if(bookedAppointment.getDate().isBefore(todayDate)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Appointment date is in past"));
		}
		appointmentService.bookAppointment(bookedAppointment);

		return new ResponseEntity<>(bookedAppointment, HttpStatus.CREATED);
	}

    @PutMapping("/controller/appointment/{appointmentId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long appointmentId, @RequestBody AppointmentRequest appointment) {
        return new ResponseEntity<>(appointmentService.updateStatus(appointmentId, appointment), HttpStatus.ACCEPTED);
    }

	@GetMapping("/controller/appointment/doctor/{statusType}/{doctorId}")
	public ResponseEntity<?> getAppointmentsByDoctor(@PathVariable Long statusType, @PathVariable Long doctorId) {
		EStatusAppointment status = EStatusAppointment.PENDING;
		if(statusType == 0) {
			status = EStatusAppointment.APROVED;
		} 
		if(statusType == 1) {
			status = EStatusAppointment.PENDING;
		}
		if(statusType == 2) {
			status = EStatusAppointment.DECLINED;
		}
		List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(status, doctorId);
		return new ResponseEntity<>(appointments, HttpStatus.OK);
	}
 
	@GetMapping("/controller/appointment/patient/{patientId}")
	public ResponseEntity<?> getAppointmentsByPatient(@PathVariable Long patientId) {
		List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
		return new ResponseEntity<>(appointments, HttpStatus.OK);
	}

}
