package pl.polsl.veterynaryclinic.validator.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.model.nondatabase.AppointmentFilter;

import java.util.Date;
import java.util.List;

@Component
public class AppointmentFilterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AppointmentFilter.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppointmentFilter appointmentFilter = (AppointmentFilter) target;

        validateTotalCosts(appointmentFilter.getMinimalTotalCost(), appointmentFilter.getMaximalTotalCost(), errors);
        validateDates(appointmentFilter.getStartingAppointmentDate(), appointmentFilter.getEndingAppointmentDate(), errors);
        validateDoctors(appointmentFilter.getDoctors(), errors);
        validatePatients(appointmentFilter.getPatients(), errors);
    }

    private void validateTotalCosts(Float minimalTotalCost, Float maximalTotalCost, Errors errors) {
        if (minimalTotalCost == null || maximalTotalCost == null)
            return;

        if (minimalTotalCost > maximalTotalCost && !errors.hasFieldErrors("minimalTotalCost"))
            errors.rejectValue("maximalTotalCost", "err_code", "Minimalny koszt nie może być wyższy niż maksymalny koszt");
    }

    private void validateDates(Date startingAppointmentDate, Date endingAppointmentDate, Errors errors) {
        if (startingAppointmentDate == null && endingAppointmentDate == null)
            return;

        if (startingAppointmentDate != null) {
            if (endingAppointmentDate != null) {
                if (startingAppointmentDate.after(endingAppointmentDate))
                    errors.rejectValue("endingAppointmentDate", "err_code", "Data początkowa nie może być późniejsza niż data końcowa");
            }
        }
    }

    private void validateDoctors(List<User> doctors, Errors errors) {
        if (doctors.isEmpty())
            errors.rejectValue("doctors", "err_code", "Musisz wybrać przynajmniej jednego lekarza");
    }

    private void validatePatients(List<Patient> patients, Errors errors) {
        if (patients.isEmpty())
            errors.rejectValue("patients", "err_code", "Musisz wybrać przynajmniej jednego pacjenta");
    }
}
