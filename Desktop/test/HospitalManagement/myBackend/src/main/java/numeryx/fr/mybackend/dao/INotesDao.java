package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotesDao extends JpaRepository<Notes, Long>{
	
	List<Notes> findAllByStatusTrue();
	List<Notes> findByPatientIdAndStatusTrue(Long id);
	List<Notes> findByDoctorIdAndStatusTrue(Long id);
}
