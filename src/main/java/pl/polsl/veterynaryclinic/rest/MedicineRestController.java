package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.dao.MedicineDao;

@RestController
@RequestMapping("/api")
public class MedicineRestController {
	
private MedicineDao medicineDao;
	
	@Autowired
	public MedicineRestController(MedicineDao theMedicineDao) {
		medicineDao = theMedicineDao;
	}
	
	@GetMapping("/medicines")
	public List<Medicine> findAll() {
		return medicineDao.findAll();
	}
	
}
