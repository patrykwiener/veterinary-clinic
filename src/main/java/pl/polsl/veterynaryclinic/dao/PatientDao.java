package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.Patient;


public interface PatientDao {

	public List<Patient> findAll();
}
