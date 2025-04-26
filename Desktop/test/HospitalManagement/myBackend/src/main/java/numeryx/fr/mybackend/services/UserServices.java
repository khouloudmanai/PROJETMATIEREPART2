package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IUserDao;
import numeryx.fr.mybackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private IUserDao userDao;


	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userDao.findByEnableTrue();
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	public void delete(Long id) {
		Optional<User> user = userDao.findById(id);
		user.get().setEnable(false);
		user.get().setStatus(false);
	}

	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public User findUserByUsername(String username) {
		User user = userDao.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return user;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return CustomnUserDetails.build(user);
	}


}
