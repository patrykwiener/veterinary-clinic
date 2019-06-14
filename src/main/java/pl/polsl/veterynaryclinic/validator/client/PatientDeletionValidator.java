package pl.polsl.veterynaryclinic.validator.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.Patient;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PatientDeletionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Patient patient = (Patient) target;

        validateDateOfDeath(patient.getDateOfDeath(), errors);
    }

    private void validateDateOfDeath(Date dateOfDeath, Errors errors) {
        if (dateOfDeath == null) {
            errors.rejectValue("dateOfDeath", "err_code", "Pole jest wymagane");
            return;
        }
        Date today = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        if (dateOfDeath.after(today)) {
            errors.rejectValue("dateOfDeath", "err_code", "Data nie może być z przyszłości");
        }

    }
}
