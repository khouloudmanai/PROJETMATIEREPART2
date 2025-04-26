package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.ISpecialistDao;
import numeryx.fr.mybackend.entities.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecialistServices {

	@Autowired
	private ISpecialistDao specialistDao;
	
	@Transactional(readOnly = true)
	public List<Specialist> findAll() {
		return (List<Specialist>) specialistDao.findAll();
	}
	
	@Transactional(readOnly = true)
	public Specialist findById(Long id) {
		return specialistDao.findById(id).orElse(null);
	}
	
	@Transactional
	public Specialist save(Specialist especialidad) {
		return specialistDao.save(especialidad);
	}
	
	public void delete(Long id) {
		specialistDao.deleteById(id);
	}
}
