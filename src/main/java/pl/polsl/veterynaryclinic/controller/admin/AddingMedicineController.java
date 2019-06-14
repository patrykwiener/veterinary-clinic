package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.MedicineDao;
import pl.polsl.veterynaryclinic.dao.UserDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.validator.admin.AddingMedicineValidator;
import pl.polsl.veterynaryclinic.validator.admin.AddingWorkerValidator;

@Controller
public class AddingMedicineController {

	private static final String PAGE_ADDRESS = "/admin/admin_medicines_add";

    private static final String PAGE_SOURCE = "/admin/admin_medicines_add";

    @Autowired
	private MedicineDao medicineDao;
    
    @Autowired
    private AddingMedicineValidator addingMedicineValidator;

    @InitBinder("medicine")
    protected void initBinder(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(addingMedicineValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddingMedicine(@ModelAttribute("medicine") @Validated Medicine medicine, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminMedicinesController.PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        medicineDao.addMedicine(medicine.getMedicineId(), medicine.getMedicineName(), medicine.getPurchasedItems(), medicine.getRemainingItems(), medicine.getUnit(), medicine.getPurchasePrice(), 
        		medicine.getPurchaseDate(), medicine.getSoldItemsPrice());
        return "redirect:/admin/admin_medicines";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddingMedicine(@ModelAttribute("medicine") Medicine medicine, @SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminMedicinesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
