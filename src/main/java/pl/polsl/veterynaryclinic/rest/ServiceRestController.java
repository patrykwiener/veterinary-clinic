package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.Service;
import pl.polsl.veterynaryclinic.dao.ServiceDao;

@RestController
@RequestMapping("/api")
public class ServiceRestController {
	
private ServiceDao serviceDao;
	
	@Autowired
	public ServiceRestController(ServiceDao theServiceDao) {
		serviceDao = theServiceDao;
	}
	
	@GetMapping("/services")
	public List<Service> findAll() {
		return serviceDao.findAll();
	}
}
