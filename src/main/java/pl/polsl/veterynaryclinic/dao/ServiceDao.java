package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.Service;


public interface ServiceDao {

	public List<Service> findAll();
	
	public boolean deleteService(Integer serviceId);
	
	public Service findService(Integer id);
	
	public boolean editService(Integer serviceId, Float serviceCost, String serviceType);
	
	public boolean addService(Integer serviceId, Float serviceCost, String serviceType);
	
}
