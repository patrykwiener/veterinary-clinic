package pl.polsl.veterynaryclinic.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.dao.AppointmentDao;

@RestController
@RequestMapping("/api")
public class AppointmentRestController {

private AppointmentDao appointmentDao;
	
	@Autowired
	public AppointmentRestController(AppointmentDao theAppointmentDao) {
		appointmentDao = theAppointmentDao;
	}
	
	@GetMapping("/appointments")
	public List<Appointment> findAll() {
		return appointmentDao.findAll();
	}
}
