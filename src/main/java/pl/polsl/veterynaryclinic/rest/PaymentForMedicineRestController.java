package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.PaymentForMedicine;
import pl.polsl.veterynaryclinic.dao.PaymentForMedicineDao;

@RestController
@RequestMapping("/api")
public class PaymentForMedicineRestController {
	private PaymentForMedicineDao paymentForMedicineDao;
	
	@Autowired
	public PaymentForMedicineRestController(PaymentForMedicineDao thePaymentForMedicineDao) {
		paymentForMedicineDao = thePaymentForMedicineDao;
	}
	
	@GetMapping("/paymentsForMedicine")
	public List<PaymentForMedicine> findAll() {
		return paymentForMedicineDao.findAll();
	}
}
