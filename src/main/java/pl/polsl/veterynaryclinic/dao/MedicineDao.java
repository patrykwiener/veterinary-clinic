package pl.polsl.veterynaryclinic.dao;
import java.sql.Date;
import java.util.List;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.User;

public interface MedicineDao {

	public List<Medicine> findAll();
	
	public boolean deleteMedicine(Integer medicineId);
	
	public Medicine findMedicine(Integer id);
	
	public boolean editMedicine(Integer medicineId, String medicineName, Integer purchasedItems, Integer remainingItems, String unit, Float purchasePrice, Date purchaseDate, Float soldItemsPrice);
	
	public boolean addMedicine(Integer medicineId, String medicineName, Integer purchasedItems, Integer remainingItems, String unit, Float purchasePrice, Date purchaseDate, Float soldItemsPrice);
	
}
