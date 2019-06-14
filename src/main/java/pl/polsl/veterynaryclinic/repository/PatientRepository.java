package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

    List<Patient> findAllByOwner(User owner);

    Patient findByPatientNameAndOwner(String patientName, User owner);

    List<Patient> findAllByPatientIdIn(List<Integer> patientIds);

    List<Patient> findDistinctByAppointmentsIn(List<Appointment> appointments);

    List<Patient> findAllByOwner_userIdAndDateOfDeathNull(Integer ownerId);

    Patient findByPatientId(Integer patientId);

    List<Patient> findAllByOwnerAndDateOfDeathIsNull(User owner);

    List<Patient> findAllByDateOfDeathIsNotNullOrderByOwner_LastName();

    List<Patient> findAllByDateOfDeathIsNullOrderByOwner_LastName();
}
