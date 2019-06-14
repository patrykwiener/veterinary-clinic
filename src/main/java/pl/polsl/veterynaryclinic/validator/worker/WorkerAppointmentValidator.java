package pl.polsl.veterynaryclinic.validator.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.WorkerAppointment;
import pl.polsl.veterynaryclinic.repository.AppointmentRepository;

import java.util.Date;

@Component
public class WorkerAppointmentValidator implements Validator {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return WorkerAppointment.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WorkerAppointment appointmentAddition = (WorkerAppointment) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientId", "error.clientId", "Musisz wybrać klienta!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientId", "error.patientId", "Musisz wybrać pacjenta!");
        validateDateTime(appointmentAddition.getAppointmentDate(), appointmentAddition.getDoctor(), appointmentAddition.getClientId(), appointmentAddition.getPatientId(), errors);

    }

    private void validateDateTime(Date appointmentDate, User doctor, Integer clientId, Integer patientId, Errors errors) {
        if (appointmentDate.before(new Date())) {
            errors.rejectValue("appointmentDate", "error.appointmentDate", "Wybrany termin musi być przyszły!");
        } else if (appointmentRepository.findByDoctorAndAppointmentDate(doctor, appointmentDate) != null) {
            errors.rejectValue("appointmentDate", "error.appointmentDate", "W wybranym terminie masz inne spotkanie! Wybierz inny termin!");
        } else if (appointmentRepository.findByAppointmentDateAndPatient_Owner_UserId(appointmentDate, clientId) != null) {
            errors.rejectValue("appointmentDate", "error.appointmentDate", "W wybranym terminie klient jest zajęty ! Wybierz inny termin!");
        }
    }
}
