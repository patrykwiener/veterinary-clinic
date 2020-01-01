package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.polsl.veterynaryclinic.model.PaymentForService;
import pl.polsl.veterynaryclinic.model.User;

import java.util.Date;
import java.util.List;

public interface PaymentForServiceRepository extends CrudRepository<PaymentForService, Integer> {

    @Query("SELECT sum(p.cost) " +
            "FROM Appointment a, PaymentForService p " +
            "WHERE p.appointment = a AND a.appointmentDate > :startDate " +
            "AND a.appointmentDate < :endDate " +
            "AND a.doctor=(" +
            "SELECT u FROM User u " +
            "WHERE u = :doctor)")
    Float findPaymentForServicesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("doctor") User doctor);

    List<PaymentForService>findAllByAppointment_AppointmentDateBetweenAndAppointment_DoctorOrderByAppointment_AppointmentDate(Date startDate, Date endDate, User doctor);
}
