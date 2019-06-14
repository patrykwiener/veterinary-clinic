package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.PaymentForService;

public interface PaymentForServiceDao {

	public List<PaymentForService> findAll();
}
