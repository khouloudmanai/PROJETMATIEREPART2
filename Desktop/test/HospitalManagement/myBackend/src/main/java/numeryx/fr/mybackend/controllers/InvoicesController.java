package numeryx.fr.mybackend.controllers;

import numeryx.fr.mybackend.entities.Invoices;
import numeryx.fr.mybackend.entities.Order;
import numeryx.fr.mybackend.entities.Patient;
import numeryx.fr.mybackend.entities.Product;
import numeryx.fr.mybackend.entities.enums.OrderStatus;
import numeryx.fr.mybackend.payload.request.InvoicesRequest;
import numeryx.fr.mybackend.payload.request.PaymentRequest;
import numeryx.fr.mybackend.payload.response.MessageResponse;
import numeryx.fr.mybackend.services.InvoicesServices;
import numeryx.fr.mybackend.services.OrderService;
import numeryx.fr.mybackend.services.PatientsServices;
import numeryx.fr.mybackend.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class InvoicesController {

	Logger logger = Logger.getLogger(AuthController.class.getName());
	@Autowired
	private InvoicesServices services;

	@Autowired
	private OrderService orderServices;

	@Autowired
	private PatientsServices patientService;

	@SuppressWarnings("unused")
	@Autowired
	private ProductServices productService;

	@GetMapping("controller/invoices")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(services.findAll());
	}

	@GetMapping("controller/invoices/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(services.findById(id));
	}

	@GetMapping("controller/invoices/person/{id}")
	public ResponseEntity<?> findInvoicesById(@PathVariable Long id) {
		return ResponseEntity.ok(services.findGrandByPersonId(id));
	}

	@PostMapping("controller/invoices")
	public ResponseEntity<?> save(@RequestBody InvoicesRequest invoiceRequest) {
		String invoiceNo = Long.toHexString(Double.doubleToLongBits(Math.random()));
		Patient patient = patientService.findById(invoiceRequest.getIdPatient());
		if (patient == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("No patient found"));
		}
		logger.log(Level.INFO, invoiceRequest.toString());
		Invoices invoice = new Invoices();
		invoice.setPatient(patient);
		invoice.setProducts(invoiceRequest.getProducts());
		float totalPrice = 0;

		for (Product product : invoice.getProducts()) {
			totalPrice += product.getPrice();
//			productService.delete(product.getId());
		}
		invoice.setStatus(OrderStatus.CREATED.toString());
		invoice.setTotalPrice(totalPrice);
		invoice.setInvoiceNumber(invoiceNo);
		invoice.setDescription(invoiceRequest.getDescription());
		return ResponseEntity.ok(services.save(invoice));
	}

	@PostMapping("/controller/invoices/success")
	public ResponseEntity<?> paymentSuccess(@RequestBody PaymentRequest request) {
		logger.info(request.toString());
		Order order = new Order();
		order.setPaypalOrderId(request.getOrderId());
		order.setPaypalOrderStatus(request.getOrderId());
		order.setCreateTime(request.getCreateTime());
		order.setUpdateTime(request.getUpdateTime());
		order.setPayerEmail(request.getPayer().getEmail_address());
		request.getPurchaseUnits().stream().forEach(e -> {
			order.setPayeeEmail(e.getPayee().getEmail_address());
			order.setAmount(e.getAmount().getValue());
			order.setCurrencyCode(e.getAmount().getCurrencyCode());
		});
		for (Invoices response : services.findByPersonId(request.getIdPerson())) {
			response.setStatus(OrderStatus.COMPLETED.toString());
			services.save(response);
		}

		return ResponseEntity.ok(orderServices.save(order));
	}

	@DeleteMapping("controller/invoices/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
//		services.delete(id);
		return ResponseEntity.ok("Role deleted");
	}

}
