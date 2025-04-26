package numeryx.fr.mybackend.payload.response;

import numeryx.fr.mybackend.entities.Invoices;

import java.util.List;


public class InvoicesResponse {

	private Long id;
	private List<Invoices> invoices;
	private Float total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Invoices> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoices> invoices) {
		this.invoices = invoices;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
}
