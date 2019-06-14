package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.veterynaryclinic.model.Service;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Integer> {

    List<Service> findAll();

}
