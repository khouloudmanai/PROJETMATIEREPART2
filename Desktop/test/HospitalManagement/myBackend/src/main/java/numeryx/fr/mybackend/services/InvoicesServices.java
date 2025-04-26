package numeryx.fr.mybackend.services;

import numeryx.fr.mybackend.dao.IInvoicesDao;
import numeryx.fr.mybackend.entities.Invoices;
import numeryx.fr.mybackend.entities.enums.OrderStatus;
import numeryx.fr.mybackend.payload.response.InvoicesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoicesServices {

	@Autowired
	private IInvoicesDao invoiceDao;

	@Transactional
	public List<Invoices> findAll() {
		return invoiceDao.findAll();
	}

	@Transactional
	public Optional<Invoices> findById(Long id) {
		return invoiceDao.findById(id);
	}

	@Transactional
	public Invoices save(Invoices invoice) {
		return invoiceDao.save(invoice);
	}

	@Transactional
	public InvoicesResponse findGrandByPersonId(Long id) {
		InvoicesResponse response = new InvoicesResponse();
		response.setId(id);
		response.setInvoices(invoiceDao.findAllByPatientPersonIdAndStatus(id, OrderStatus.CREATED.toString()));
		response.setTotal(getTotalPrice(id));
		return response;
	}

	@Transactional
	public List<Invoices> findByPersonId(Long id) {
		return invoiceDao.findAllByPatientPersonIdAndStatus(id, OrderStatus.CREATED.toString());
	}
	
	public float getTotalPrice(Long id) {
		List<Invoices> invoice = invoiceDao.findAllByPatientPersonIdAndStatus(id, OrderStatus.CREATED.toString());
		float totalPrice = 0;

		for (Invoices invoices : invoice) {
			totalPrice += invoices.getTotalPrice();
		}

		return totalPrice;
	}

}
