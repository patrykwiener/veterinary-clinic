package pl.polsl.veterynaryclinic.validator.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.polsl.veterynaryclinic.dao.ServiceDao;
import pl.polsl.veterynaryclinic.model.Service;

@Component
public class AddingServiceValidator implements Validator {
	
	@Autowired
	ServiceDao serviceDao;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return Service.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        validateIfAnyEmptyOrWhitespace(errors);

        Service service = (Service) target;

        validateNumbers(service, errors);
        validateSameType(service.getServiceType(), errors);
    }

    private void validateIfAnyEmptyOrWhitespace(Errors errors) {
        List<String> formFields = new ArrayList<>(Arrays.asList("serviceType", "serviceCost"));

        for (String field : formFields)
        	ValidationUtils.rejectIfEmpty(errors, field, "err_code", "Pole jest wymagane");
        
        
    }

    private void validateNumbers(Service service, Errors errors) {
        if (errors.hasFieldErrors("serviceCost"))
            return;

        if (service.getServiceCost().equals(null) || service.getServiceCost() < 0)
            errors.rejectValue("serviceCost", "err_code", "Cena usługi musi być większa od zera i być wartością zmiennoprzecinkową");
    }
    
    private void validateSameType(String type, Errors errors) {
        if (errors.hasFieldErrors("serviceType"))
            return;

        List<Service> services = serviceDao.findAll();
        
        for (Service s : services) {
        	if (type.toUpperCase().equals(s.getServiceType().toUpperCase()))
        		errors.rejectValue("serviceCost", "err_code", "Taka usługa już istnieje w bazie");
        }
        
    }

}
