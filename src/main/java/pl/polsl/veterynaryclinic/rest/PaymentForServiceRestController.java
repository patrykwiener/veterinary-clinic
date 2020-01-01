package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.PaymentForService;
import pl.polsl.veterynaryclinic.dao.PaymentForServiceDao;

@RestController
@RequestMapping("/api")
public class PaymentForServiceRestController {
private PaymentForServiceDao paymentForServiceDao;
	
	@Autowired
	public PaymentForServiceRestController(PaymentForServiceDao thePaymentForServiceDao) {
		paymentForServiceDao = thePaymentForServiceDao;
	}
	
	@GetMapping("/paymentsForService")
	public List<PaymentForService> findAll() {
		return paymentForServiceDao.findAll();
	}
}
