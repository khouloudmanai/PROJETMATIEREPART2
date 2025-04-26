package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IAppointmentDao;
import numeryx.fr.mybackend.entities.Appointment;
import numeryx.fr.mybackend.entities.enums.EStatusAppointment;
import numeryx.fr.mybackend.payload.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

	@Autowired
	private IAppointmentDao appointmentRepository;

	@Transactional
	public Appointment bookAppointment(Appointment appointment) throws Exception {

		appointment.setStatus(EStatusAppointment.PENDING);
		return appointmentRepository.save(appointment);
	}

	public Appointment updateStatus(Long appointmentId, AppointmentRequest appointment) {

		Optional<Appointment> appointmentList = appointmentRepository.findById(appointmentId);

		if (appointmentList.isPresent()) {
			if (appointment.getStatus() != null) {
				appointmentList.get().setStatus(appointment.getStatus());
			}
			return appointmentRepository.save(appointmentList.get());
		}
		return null;
	}

	@Transactional(readOnly = true)
	public List<Appointment> findByDoctorId(Long doctorId) {
		return appointmentRepository.findByDoctorId(doctorId);
	}


	@Transactional(readOnly = true)
	public List<Appointment> getAppointmentsByDoctor(EStatusAppointment status, Long doctorId) {
		return appointmentRepository.findByStatusAndDoctorId(status, doctorId);
	}

	@Transactional(readOnly = true)
	public List<Appointment> getAppointmentsByPatient(Long patientId) {
		return appointmentRepository.findByPatientId(patientId);
	}

	@Transactional
	public void delete(List<Appointment> app) throws Exception {
		appointmentRepository.deleteAll(app);
	}

}
