package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPatientsDao extends JpaRepository<Patient, Long>{

	Patient findPatientByPersonEmail(String email);
	List<Patient> findByPersonEnableTrue();
}
