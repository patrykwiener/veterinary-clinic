package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.polsl.veterynaryclinic.model.PaymentForMedicine;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.PaymentForMedicineSummary;

import java.util.Date;
import java.util.List;

public interface PaymentForMedicineRepository extends CrudRepository<PaymentForMedicine, Integer> {

    @Query("SELECT new pl.polsl.veterynaryclinic.model.nondatabase.PaymentForMedicineSummary(SUM(m.purchasePrice * p.quantity), SUM(p.cost * p.quantity)) " +
            "FROM PaymentForMedicine p, Medicine m, Appointment a " +
            "WHERE p.medicine = m " +
            "AND p.appointment = a " +
            "AND a.appointmentDate > :startDate " +
            "AND a.appointmentDate < :endDate " +
            "AND a.doctor = :doctor")
    PaymentForMedicineSummary findPaymentForMedicinesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("doctor") User doctor);

    List<PaymentForMedicine> findAllByAppointment_AppointmentDateBetweenAndAppointment_DoctorOrderByAppointment_AppointmentDate(Date startDate, Date endDate, User doctor);

}
