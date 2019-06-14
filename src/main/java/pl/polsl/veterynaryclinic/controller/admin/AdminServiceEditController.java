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
import pl.polsl.veterynaryclinic.dao.ServiceDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.Service;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.validator.admin.AdminMedicinesEditValidator;
import pl.polsl.veterynaryclinic.validator.admin.AdminServicesEditValidator;

@Controller
public class AdminServiceEditController {

	private static final String PAGE_ADDRESS = "/admin/admin_services_edit";

    private static final String PAGE_SOURCE = "/admin/admin_services_edit";

    @Autowired
	private ServiceDao serviceDao;
    
    @Autowired
    private AdminServicesEditValidator adminServicesEditValidator;

    @InitBinder("newEditedService")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(adminServicesEditValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAdminServicesEdit(@SessionAttribute("editedService") Service editedService, @ModelAttribute("newEditedService") @Validated Service newEditedService, BindingResult bindingResult, Model model, @RequestParam String action) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editedService", editedService);
                model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminServicesController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            serviceDao.editService(editedService.getServiceId(), newEditedService.getServiceCost(), newEditedService.getServiceType());
            return "redirect:/admin/admin_services";
        }
        return "redirect:/admin/admin_services";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminServicesEdit(@SessionAttribute("loggedUser") User loggedUser, @SessionAttribute("editedService") Service editedService, @ModelAttribute("newEditedService") Service newEditedService, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        if (editedService.getServiceId() == null)
            return "redirect:/admin/admin_services";
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("editedService", editedService);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminServicesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
