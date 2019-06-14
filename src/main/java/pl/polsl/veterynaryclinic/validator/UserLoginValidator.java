package pl.polsl.veterynaryclinic.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserLoginValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        validateIfAnyEmptyOrWhitespace(errors);

        User user = (User) target;

        validateIfUserExists(user.getLogin(), errors);
        validateUserPassword(user.getLogin(), user.getPassword(), errors);
    }

    private void validateIfAnyEmptyOrWhitespace(Errors errors) {
        List<String> formFields = new ArrayList<>(Arrays.asList("login", "password"));

        for (String field : formFields)
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "err_code", "Pole jest wymagane");
    }

    private void validateIfUserExists(String login, Errors errors) {
        if (errors.hasFieldErrors("login"))
            return;

        if (userRepository.findByLogin(login) == null)
            errors.rejectValue("login", "err_code", "Użytkownik z takim loginem nie istnieje");

    }

    private void validateUserPassword(String login, String password, Errors errors) {
        if (errors.hasFieldErrors("login") || errors.hasFieldErrors("password"))
            return;

        User user = userRepository.findByLogin(login);
        if (!user.getPassword().equals(password))
            errors.rejectValue("password", "err_code", "Błędne hasło");
    }
}
