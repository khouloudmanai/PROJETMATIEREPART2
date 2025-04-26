package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IDoctorDao;
import numeryx.fr.mybackend.dao.IPatientsDao;
import numeryx.fr.mybackend.entities.Doctor;
import numeryx.fr.mybackend.entities.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorPatientService {

	@Autowired
	private IPatientsDao servicePatient;
	@Autowired
	private IDoctorDao serviceDoctor;

	Logger logger = (Logger) LoggerFactory.getLogger(DoctorPatientService.class);

	@Transactional
	public Patient assignRelationShip(Long patientId, Long doctorId) throws Exception {
		logger.info("Assigning doctor with Doctor Id : {} to patient with Patient Id : {}", doctorId, patientId);
		
		Patient patient = servicePatient.findById(patientId)
				.orElseThrow(() -> new Exception("Patient  Id " + patientId));
		Doctor doctor = serviceDoctor.findById(doctorId)
				.orElseThrow(() -> new Exception("Doctor  Id " + doctorId));
		doctor.addPatient(patient);
		return servicePatient.save(patient);
	}

	@Transactional(readOnly = true)
	public void removeRelation(Long doctorId, Long patientId) throws Exception {
		logger.info("Deleting relationship doctor id: {} to patient with Patient Id : {}", doctorId, patientId);
		Patient patient = servicePatient.findById(patientId)
				.orElseThrow(() -> new Exception("Patient  Id " + patientId));
		Doctor doctor = serviceDoctor.findById(doctorId)
				.orElseThrow(() -> new Exception("Doctor  Id " + doctorId));
		
		patient.setDoctor(null);
		doctor.removePatient(patient);
		servicePatient.save(patient);
		serviceDoctor.save(doctor);
		
	}
}
