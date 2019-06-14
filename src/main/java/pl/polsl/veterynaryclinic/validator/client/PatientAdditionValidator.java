package pl.polsl.veterynaryclinic.validator.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;

import javax.servlet.http.HttpSession;

@Component
public class PatientAdditionValidator implements Validator {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        validateIfAnyEmptyOrWhitespace(errors);

        Patient patient = (Patient) target;

        validateIfNameExists(patient.getPatientName(), errors);
    }

    private void validateIfAnyEmptyOrWhitespace(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientName", "err_code", "Pole jest wymagane");
    }

    private void validateIfNameExists(String patientName, Errors errors) {
        if (errors.hasFieldErrors("patientName"))
            return;
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (patientRepository.findByPatientNameAndOwner(patientName, loggedUser) != null)
            errors.rejectValue("patientName", "err_code", "Zwierzak o takim imieniu istnieje");
    }
}
