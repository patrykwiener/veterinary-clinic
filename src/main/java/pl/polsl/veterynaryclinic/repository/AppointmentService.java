package pl.polsl.veterynaryclinic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.polsl.veterynaryclinic.model.Appointment;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public List<Appointment> findAllAppointmentsDynamically(final Float minimalCost, final Float maximalCost, final Date startDate, final Date endDate, final List<User> doctors, final List<Patient> patients, final Integer hasTakenPlace) {
        return appointmentRepository.findAll(new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (minimalCost != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.ge(root.get("totalCost"), minimalCost)));
                }
                if (maximalCost != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.le(root.get("totalCost"), maximalCost)));
                }
                if (startDate != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("appointmentDate"), startDate)));
                }
                if (endDate != null) {
                    LocalDate dateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Date properEndDate = Date.from(dateTime.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("appointmentDate"), properEndDate)));
                }
                if (doctors != null) {
                    predicates.add(criteriaBuilder.and(root.get("doctor").in(doctors)));
                }
                if (patients != null) {
                    predicates.add(criteriaBuilder.and(root.get("patient").in(patients)));
                }
                if (hasTakenPlace != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("hasTakenPlace"), hasTakenPlace)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
