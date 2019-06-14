package pl.polsl.veterynaryclinic.validator.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.polsl.veterynaryclinic.dao.UserDao;
import pl.polsl.veterynaryclinic.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AdminWorkersEditValidator implements Validator {

	@Autowired
	UserDao userDao;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        validateIfAnyEmptyOrWhitespace(errors);

        User user = (User) target;


        validateIfUserExists(user.getLogin(), user.getUserId(), errors);
        validatePassword(user.getPassword(), user.getConfirmPassword(), errors);
        validatePhoneNumber(user.getPhoneNumber(), errors);
    }

    private void validateIfAnyEmptyOrWhitespace(Errors errors) {
        List<String> formFields = new ArrayList<>(Arrays.asList("firstName", "lastName", "login", "password", "confirmPassword", "phoneNumber"));

        for (String field : formFields)
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "err_code", "Pole jest wymagane");
    }

    private void validateIfUserExists(String login, Integer id, Errors errors) {
        if (errors.hasFieldErrors("login"))
            return;

        List<User> users = userDao.findAll();
        for (User user : users) {
        	if (user.getLogin().equals(login))
        		if (!(user.getUserId().equals(id)))
        			errors.rejectValue("login", "err_code", "Użytkownik z takim loginem już istnieje");
        }

    }

    private void validatePassword(String password, String confirmPassword, Errors errors) {
        if (errors.hasFieldErrors("password") || errors.hasFieldErrors("confirmPassword"))
            return;

        if (!password.equals(confirmPassword))
            errors.rejectValue("confirmPassword", "err_code", "Hasła muszą być jednakowe");
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        if (errors.hasFieldErrors("phoneNumber"))
            return;
        Pattern pattern = Pattern.compile("[0-9]{9}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches())
            errors.rejectValue("phoneNumber", "err_code", "Błędny numer telefonu: musi być dokładnie 9-cyfrowy");

    }
	
	
}
