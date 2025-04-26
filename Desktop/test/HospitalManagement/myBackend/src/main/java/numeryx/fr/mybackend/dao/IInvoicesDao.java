package numeryx.fr.mybackend.dao;

import numeryx.fr.mybackend.entities.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInvoicesDao extends JpaRepository<Invoices, Long>{
	List<Invoices> findAllByStatus(String status);
	List<Invoices> findAllByPatientPersonIdAndStatus(Long id, String status); 
	List<Invoices> findTotalPriceByPatientId(Long id);
}