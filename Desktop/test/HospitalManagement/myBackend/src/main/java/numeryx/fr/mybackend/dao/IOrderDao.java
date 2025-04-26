package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDao extends JpaRepository<Order, Long>{

    Order findByPaypalOrderId(String paypalOrderId);
}
