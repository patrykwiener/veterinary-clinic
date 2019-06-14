package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByLogin(String login);

    List<User> findAllByUserType(Character type);

    List<User> findDistinctByPets_AppointmentsIn(List<Appointment> appointments);

    User findByUserId(Integer userId);

}
