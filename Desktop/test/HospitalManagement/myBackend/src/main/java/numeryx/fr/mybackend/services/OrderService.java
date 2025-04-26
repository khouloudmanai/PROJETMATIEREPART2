package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IOrderDao;
import numeryx.fr.mybackend.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	private IOrderDao orderDao;

	@Transactional
	public List<Order> findAll() {
		return orderDao.findAll();
	}

	@Transactional
	public Optional<Order> findById(Long id) {
		return orderDao.findById(id);
	}

	@Transactional
	public Order save(Order order) {
		return orderDao.save(order);
	}

}
