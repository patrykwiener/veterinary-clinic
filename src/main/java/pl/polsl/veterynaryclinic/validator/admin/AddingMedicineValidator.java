package pl.polsl.veterynaryclinic.validator.admin;

import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.polsl.veterynaryclinic.dao.MedicineDao;
import pl.polsl.veterynaryclinic.model.Medicine;

@Component
public class AddingMedicineValidator implements Validator {

	@Autowired
	MedicineDao medicineDao;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return Medicine.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	validateDate((String)errors.getFieldValue("purchaseDate"), errors);
        validateIfAnyEmptyOrWhitespace(errors);

        Medicine medicine = (Medicine) target;

        validateNumbers(medicine, errors);
    }

    private void validateIfAnyEmptyOrWhitespace(Errors errors) {
        List<String> formFields = new ArrayList<>(Arrays.asList("purchasedItems", "remainingItems", "purchasePrice", "soldItemsPrice"));

        for (String field : formFields)
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "err_code", "Pole jest wymagane");
        
        List<String> formFieldsWhitespace = new ArrayList<>(Arrays.asList("medicineName", "unit"));
        for (String fieldWhitespace : formFieldsWhitespace)
            ValidationUtils.rejectIfEmpty(errors, fieldWhitespace, "err_code", "Pole jest wymagane");
    }

    private void validateNumbers(Medicine medicine, Errors errors) {
        if (errors.hasFieldErrors("purchasedItems") || errors.hasFieldErrors("remainingItems") || errors.hasFieldErrors("purchasePrice") || errors.hasFieldErrors("purchaseDate") || errors.hasFieldErrors("soldItemsPrice"))
            return;

        if (medicine.getPurchasedItems().equals(null) || medicine.getPurchasedItems() < 0)
            errors.rejectValue("purchasedItems", "err_code", "Liczba zakupionych sztuk musi być liczbą całkowitą i nieujemną");
        
        if (medicine.getRemainingItems().equals(null) || medicine.getRemainingItems() < 0)
            errors.rejectValue("remainingItems", "err_code", "Liczba pozostałych sztuk musi być liczbą całkowitą i nieujemną");
        
        if (medicine.getPurchasePrice().equals(null) || medicine.getPurchasePrice() < 0)
            errors.rejectValue("purchasePrice", "err_code", "Cena kupna musi być liczbą zmiennoprzecinkową i nieujemną");
        
        if (medicine.getSoldItemsPrice().equals(null) || medicine.getSoldItemsPrice() < 0)
            errors.rejectValue("soldItemsPrice", "err_code", "Cena sprzedaży sztuki musi być liczbą zmiennoprzecinkową i nieujemną");
        
        if (medicine.getPurchasedItems() < medicine.getRemainingItems())
            errors.rejectValue("purchasedItems", "err_code", "Liczba zakupionych sztuk nie może być mniejsza od ilości pozostałych");
        
    }
    
    private void validateDate(String date, Errors errors) {
    	if (errors.hasFieldErrors("purchaseDate")) {
       		errors.rejectValue("purchaseDate", "err_code", "Błędna data: format yyyy-MM-dd");
    		return;
    	}
    	if (date == null) {
    		errors.rejectValue("purchaseDate", "err_code", "Błędna data: format yyyy-MM-dd");
    		return;
    	}
    	Pattern pattern = Pattern.compile("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
      	      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
      	      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
      	      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
      Matcher matcher = pattern.matcher(date);
      if (!matcher.matches()) {
          errors.rejectValue("purchaseDate", "err_code", "Błędna data: format yyyy-MM-dd");
          return;
      }
      
      java.util.Date correctDate;
      try {
    	  correctDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    	  boolean correctDateFlag = new java.util.Date().before(correctDate);
          
          if (correctDateFlag) {
        	  errors.rejectValue("purchaseDate", "err_code", "Data nie może być z przeszłości");
          }
          
      } catch (ParseException e) {
    	  errors.rejectValue("purchaseDate", "err_code", "Błędna data: format yyyy-MM-dd");
      }
    	  
    }
	
}
