package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IConfirmationToken;
import numeryx.fr.mybackend.dao.IUserDao;
import numeryx.fr.mybackend.entities.ConfirmationToken;
import numeryx.fr.mybackend.entities.User;
import numeryx.fr.mybackend.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConfirmationTokenService {

	@Autowired
	private IConfirmationToken confirmationTokenRepository;

	@Autowired
	private IUserDao userRepository;

	@Transactional(readOnly = true)
	public Optional<ConfirmationToken> findByConfirmationToken(String token) {
		if (confirmationTokenRepository.findByConfirmationToken(token) == null) {
			throw new TokenRefreshException("Invalid code");
		}
		return Optional.of(confirmationTokenRepository.findByConfirmationToken(token));
	}

	@Transactional
	public ConfirmationToken createConfirmationToken(Long userId) {

		Random random = new Random();
		int max = 90000;
		int min = 10000;
		String token = Integer.toString(random.nextInt(max - min + 1) + min);

		ConfirmationToken confirmationToken = new ConfirmationToken();

		confirmationToken.setUser(userRepository.findById(userId).get());
		confirmationToken.setCreatedDate(new Date());
		confirmationToken.setStatus("PENDING");
		confirmationToken.setConfirmationToken(token);

		confirmationToken = confirmationTokenRepository.save(confirmationToken);
		return confirmationToken;
	}

	@Transactional
	public ConfirmationToken save(ConfirmationToken token) {
		return confirmationTokenRepository.save(token);
	}

	@Transactional(readOnly = true)
	public List<ConfirmationToken> findByUser(User user) {
		return confirmationTokenRepository.findByUser(user);
	}

	@Transactional
	public void delete(ConfirmationToken token) {
		ConfirmationToken tokenFind = confirmationTokenRepository.findByConfirmationToken(token.getConfirmationToken());
		tokenFind.setStatus("DISABLED");
	}
}