package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.PaymentForMedicine;


public interface PaymentForMedicineDao {

	public List<PaymentForMedicine> findAll();
}
