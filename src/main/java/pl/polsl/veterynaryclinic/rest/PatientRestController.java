package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.dao.PatientDao;

@RestController
@RequestMapping("/api")
public class PatientRestController {

private PatientDao patientDao;
	
	@Autowired
	public PatientRestController(PatientDao thePatientDao) {
		patientDao = thePatientDao;
	}
	
	@GetMapping("/patients")
	public List<Patient> findAll() {
		return patientDao.findAll();
	}
}
