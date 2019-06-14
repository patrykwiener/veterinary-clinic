package pl.polsl.veterynaryclinic.validator.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.Patient;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.PatientRepository;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WorkerPatientValidator implements Validator {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Patient newPatient = (Patient) target;
        validateIfAnyEmptyOrWhitespace(newPatient.getPatientName(), errors);
        validateIfOnlyLetters(newPatient.getPatientName(), errors);
        validateIfNameExists(newPatient.getPatientName(), newPatient.getOwner(), errors);
    }


    private void validateIfAnyEmptyOrWhitespace(String patientName, Errors errors) {
        if (containsWhitespace(patientName)) {
            errors.rejectValue("patientName", "error.patientName", "W polu nie mogą występować białe znaki!");
        }
    }

    private void validateIfNameExists(String patientName, User owner, Errors errors) {
        if (errors.hasFieldErrors("patientName"))
            return;
        Patient patientToEdit = (Patient) session.getAttribute("patientToEdit");
        if (patientToEdit == null) {
            if (patientRepository.findByPatientNameAndOwner(patientName, owner) != null)
                errors.rejectValue("patientName", "err_code", "Właściciel posiada już zwierzaka o takim imieniu!");
        } else{
            if (!patientToEdit.getPatientName().equals(patientName) && patientRepository.findByPatientNameAndOwner(patientName, owner) != null)
                errors.rejectValue("patientName", "err_code", "Właściciel posiada już zwierzaka o takim imieniu!");
        }
    }

    private void validateIfOnlyLetters(String patientName, Errors errors) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        if (!errors.hasFieldErrors("patientName")) {
            matcher = pattern.matcher(patientName);
            if (!matcher.matches()) {
                errors.rejectValue("patientName", "error.patientName", "Imię może zawierać tylko litery alfabetu!");
            }
        }
    }


    private boolean containsWhitespace(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }


}
