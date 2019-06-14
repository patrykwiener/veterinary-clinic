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
import pl.polsl.veterynaryclinic.dao.ServiceDao;
import pl.polsl.veterynaryclinic.dao.UserDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.Service;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.validator.admin.AddingMedicineValidator;
import pl.polsl.veterynaryclinic.validator.admin.AddingServiceValidator;
import pl.polsl.veterynaryclinic.validator.admin.AddingWorkerValidator;

@Controller
public class AddingServiceController {

	private static final String PAGE_ADDRESS = "/admin/admin_services_add";

    private static final String PAGE_SOURCE = "/admin/admin_services_add";

    @Autowired
	private ServiceDao serviceDao;
    
    @Autowired
    private AddingServiceValidator addingServiceValidator;

    @InitBinder("service")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addingServiceValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddingService(@ModelAttribute("service") @Validated Service service, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminServicesController.PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        serviceDao.addService(service.getServiceId(), service.getServiceCost(), service.getServiceType());
        return "redirect:/admin/admin_services";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddingService(@ModelAttribute("service") Service service, @SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminServicesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
	
}
