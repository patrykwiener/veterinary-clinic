package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>, JpaSpecificationExecutor<Appointment> {

    Appointment findByAppointmentDate(Date date);

    @Transactional
    Long deleteByPatientAndHasTakenPlace(Patient patient, Integer hasTakenPlace);

    List<Appointment> findAllByDoctorAndHasTakenPlaceOrderByAppointmentDate(User doctor, Integer hasTakenPlace);

    List<Appointment> findAllByDoctorAndAppointmentDateGreaterThanOrderByAppointmentDate(User doctor, Date currentDate);

    List<Appointment> findAllByDoctorAndHasTakenPlaceAndPatient_Owner_UserIdOrderByAppointmentDate(User doctor,
                                                                                                   Integer hasTakenPlace,
                                                                                                   Integer clientId);

    List<Appointment> findAllByDoctorAndAppointmentDateGreaterThanAndPatient_Owner_UserIdOrderByAppointmentDate(User doctor,
                                                                                                                Date currentDate,
                                                                                                                Integer clientId);

    List<Appointment> findAllByDoctorAndHasTakenPlaceAndPatient_PatientIdAndPatient_Owner_UserIdOrderByAppointmentDate(
            User doctor,
            Integer hasTakenPlace,
            Integer patientId,
            Integer ownerId);

    List<Appointment> findAllByDoctorAndPatient_PatientIdAndPatient_Owner_UserIdAndAppointmentDateGreaterThanOrderByAppointmentDate(
            User doctor,
            Integer patientId,
            Integer ownerId,
            Date currentDate);

    Appointment findByDoctorAndAppointmentDate(User doctor, Date appointmentDate);

    Appointment findByAppointmentDateAndPatient_Owner_UserId(Date appointmentDate, Integer clientId);

    Appointment findByAppointmentId(Integer appointmentId);

    List<Appointment> findAllByDoctorAndAppointmentDateBetweenAndHasTakenPlaceOrderByAppointmentDate(User doctor, Date currentDate, Date tomorrowDate, Integer hasTakenPlace);

    List<Appointment> findAllByDoctorAndAppointmentDateBetweenAndPatient_Owner_UserIdAndHasTakenPlaceOrderByAppointmentDate(User doctor,
                                                                                                                            Date currentDate,
                                                                                                                            Date tomorrowDate,
                                                                                                                            Integer clientId,
                                                                                                                            Integer hasTakenPlace);

    List<Appointment> findAllByDoctorAndPatient_PatientIdAndPatient_Owner_UserIdAndAppointmentDateBetweenAndHasTakenPlaceOrderByAppointmentDate(
            User doctor,
            Integer patientId,
            Integer ownerId,
            Date currentDate,
            Date tomorrowDate,
            Integer hasTakenPlace);
}
