package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.Appointment;


public interface AppointmentDao {

	public List<Appointment> findAll();
}
