package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.MedicineDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.validator.admin.AdminMedicinesEditValidator;

@Controller
public class AdminMedicinesEditController {

	private static final String PAGE_ADDRESS = "/admin/admin_medicines_edit";

    private static final String PAGE_SOURCE = "/admin/admin_medicines_edit";

    @Autowired
	private MedicineDao medicineDao;
    
    @Autowired
    private AdminMedicinesEditValidator adminMedicinesEditValidator;

    @InitBinder("newEditedMedicine")
    protected void initBinder(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.addValidators(adminMedicinesEditValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAdminMedicinesEdit(@SessionAttribute("editedMedicine") Medicine editedMedicine, @ModelAttribute("newEditedMedicine") @Validated Medicine newEditedMedicine, BindingResult bindingResult, Model model, @RequestParam String action) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editedMedicine", editedMedicine);
                model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminMedicinesController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            medicineDao.editMedicine(editedMedicine.getMedicineId(), newEditedMedicine.getMedicineName(), newEditedMedicine.getPurchasedItems(), newEditedMedicine.getRemainingItems(), 
            		newEditedMedicine.getUnit(), newEditedMedicine.getPurchasePrice(), newEditedMedicine.getPurchaseDate(), newEditedMedicine.getSoldItemsPrice());
            return "redirect:/admin/admin_medicines";
        }
        return "redirect:/admin/admin_medicines";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminMedicinesEdit(@SessionAttribute("loggedUser") User loggedUser, @SessionAttribute("editedMedicine") Medicine editedMedicine, @ModelAttribute("newEditedMedicine") Medicine newEditedMedicine, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        if (editedMedicine.getMedicineId() == null)
            return "redirect:/admin/admin_medicines";
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("editedMedicine", editedMedicine);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminMedicinesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
