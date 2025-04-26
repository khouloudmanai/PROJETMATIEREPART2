package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IRoleDao;
import numeryx.fr.mybackend.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
public class RoleServices {

	@Autowired
	private IRoleDao roleDao;
	
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return (List<Role>) roleDao.findAll();
	}
	
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		return roleDao.findById(id).orElse(null);
	}
	
	@Transactional
	public Role save(Role role) {
		return roleDao.save(role);
	}
	
	public void delete(Long id) {
		roleDao.deleteById(id);
	}
	@Transactional
    public Set<Role> getRolesByIdIn(Set<Long> roleNames) {
        return roleDao.findByIdIn(roleNames);
    }
}
