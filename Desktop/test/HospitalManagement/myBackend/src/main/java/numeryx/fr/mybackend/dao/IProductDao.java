package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long>{
	
	List<Product> findByEnableTrue();
	Product findByName(String name);
	Product findByCode(String code);
} 