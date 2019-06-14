package pl.polsl.veterynaryclinic.validator.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WorkerAddDataValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        validateIfEmpty(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getPhoneNumber(), errors);
        validateIfWhitespace(user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getPhoneNumber(), errors);
        validateIfOnlyLetters(user.getFirstName(), user.getLastName(), errors);
        validateIfUserExists(user.getUserId(), user.getLogin(), errors);
        validatePhoneNumber(user.getPhoneNumber(), errors);
        validatePassword(user.getPassword(), user.getConfirmPassword(), errors);
    }

    private void validateIfWhitespace(String firstName, String lastName, String login, String password, String phoneNumber, Errors errors) {
        if (containsWhitespace(firstName)) {
            errors.rejectValue("firstName", "error.firstName", "W polu nie mogą występować białe znaki!");
        }
        if (containsWhitespace(lastName)) {
            errors.rejectValue("lastName", "error.lastName", "W polu nie mogą występować białe znaki!");
        }
        if (containsWhitespace(login)) {
            errors.rejectValue("login", "error.login", "W polu nie mogą występować białe znaki!");
        }
        if (containsWhitespace(password)) {
            errors.rejectValue("password", "error.password", "W polu nie mogą występować białe znaki!");
        }
        if (containsWhitespace(phoneNumber)) {
            errors.rejectValue("phoneNumber", "error.phoneNumber", "W polu nie mogą występować białe znaki!");
        }
    }

    private void validateIfEmpty(String firstName, String lastName, String login, String password, String phoneNumber, Errors errors) {
        if (firstName.isEmpty()) {
            errors.rejectValue("firstName", "error.firstName", "Pole nie może być puste!");
        }
        if (lastName.isEmpty()) {
            errors.rejectValue("lastName", "error.lastName", "Pole nie może być puste!");
        }
        if (login.isEmpty()) {
            errors.rejectValue("login", "error.login", "Pole nie może być puste!");
        }
        if (password.isEmpty()) {
            errors.rejectValue("password", "error.password", "Pole nie może być puste!");
        }
        if (phoneNumber.isEmpty()) {
            errors.rejectValue("phoneNumber", "error.phoneNumber", "Pole nie może być puste!");
        }
    }

    private void validateIfUserExists(Integer id, String login, Errors errors) {
        if (errors.hasFieldErrors("login"))
            return;

        User user = userRepository.findByLogin(login);
        User previousUser = (User) session.getAttribute("previousUser");
        if (user != null) {
            if (previousUser != null) {
                if (!user.getUserId().equals(id)) {
                    errors.rejectValue("login", "error.login", "Użytkownik z takim loginem już istnieje!");
                }
            } else {
                errors.rejectValue("login", "error.login", "Użytkownik z takim loginem już istnieje!");
            }
        }
    }

    private void validateIfOnlyLetters(String firstName, String lastName, Errors errors) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        if (!errors.hasFieldErrors("firstName")) {
            matcher = pattern.matcher(firstName);
            if (!matcher.matches()) {
                errors.rejectValue("firstName", "error.firstName", "Imię może zawierać tylko litery alfabetu!");
            }
        }
        if (!errors.hasFieldErrors("lastName")) {
            matcher = pattern.matcher(lastName);
            if (!matcher.matches()) {
                errors.rejectValue("lastName", "error.lastName", "Nazwisko może zawierać tylko litery alfabetu!");
            }
        }
    }

    private void validatePassword(String password, String confirmPassword, Errors errors) {
        if (errors.hasFieldErrors("password") || errors.hasFieldErrors("confirmPassword"))
            return;

        User previousUser = (User) session.getAttribute("previousUser");
        if (!password.equals(confirmPassword)) {
            if (previousUser != null) {
                if (!previousUser.getPassword().equals(password)) {
                    errors.rejectValue("confirmPassword", "error.confirmPassword", "Hasła muszą być jednakowe");
                }
            } else {
                errors.rejectValue("confirmPassword", "error.confirmPassword", "Hasła muszą być jednakowe");
            }
        }
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        if (errors.hasFieldErrors("phoneNumber"))
            return;
        Pattern pattern = Pattern.compile("[0-9]{9}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches())
            errors.rejectValue("phoneNumber", "error.phoneNumber", "Numer powinien się składać z 9 cyfr!");
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
