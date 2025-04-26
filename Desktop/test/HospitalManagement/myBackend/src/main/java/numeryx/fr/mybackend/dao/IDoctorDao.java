package numeryx.fr.mybackend.dao;


import numeryx.fr.mybackend.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface IDoctorDao extends JpaRepository<Doctor, Long>{
	
	List<Doctor> findDoctorBySpecializationId(Long id);
	Optional<Doctor> findDoctorByPersonEmail(String email);
	List<Doctor> findByPersonEnableTrue();
}
 