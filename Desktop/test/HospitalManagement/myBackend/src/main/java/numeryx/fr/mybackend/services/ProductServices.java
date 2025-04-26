package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IProductDao;
import numeryx.fr.mybackend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServices {

	@Autowired
	private IProductDao productoDao;

	@Transactional
	public List<Product> findAll() {
		return productoDao.findByEnableTrue();
	}

	@Transactional
	public Product findById(Long id) {
		return productoDao.findById(id).get();
	}

	@Transactional
	public Product findByCode(String code) {
		return productoDao.findByCode(code);
	}

	@Transactional
	public Product save(Product product) {
//
//		if (existingProduct != null) {
//			int quantity = existingProduct.getQuantity();
//			quantity++;
//			existingProduct.setQuantity(quantity);
//
//			productoDao.save(existingProduct);
//		} else {
//			int quantity = product.getQuantity();
//			quantity++;
//			product.setQuantity(quantity);
	//			}
		return productoDao.save(product);
	}

	@Transactional
	public Product findByName(String name) {
		return productoDao.findByName(name);
	}

	@Transactional
	public void delete(Long id) {
		Product product = findById(id);
		product.setEnable(false);
	}

}
