package pl.polsl.veterynaryclinic.controller.admin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.MedicineDao;
import pl.polsl.veterynaryclinic.dao.ServiceDao;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.Service;
import pl.polsl.veterynaryclinic.model.User;

@Controller
@SessionAttributes("editedService")
public class AdminServicesController {
	
	@Autowired
	private ServiceDao serviceDao;

	static final String PAGE_ADDRESS = "/admin/admin_services";

    private static final String PAGE_SOURCE = "/admin/admin_services";

    @PostMapping(PAGE_ADDRESS)
    public String postAdminMedicines(@ModelAttribute("service") Service service, @RequestParam String action, Model model) {
        switch (action) {
            case "add":
                return "redirect:/admin/admin_services_add";
            //FOR FUTURE DEVELOPEMENT: removing services
            /*case "remove":
            	if (service.getServiceId() == null)
                    return "redirect:/admin/admin_services";
            	serviceDao.deleteService(service.getServiceId());
                return "redirect:/admin/admin_services";*/
            case "edit":
                if (service.getServiceId() == null)
                    return "redirect:/admin/admin_services";
                
                model.addAttribute("editedService", serviceDao.findService(service.getServiceId()));
                return "redirect:/admin/admin_services_edit";
        }
        return "redirect:/admin/admin_services";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminMedicines(@ModelAttribute("service") Service service, @SessionAttribute("loggedUser") User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");


        List<Service> services = serviceDao.findAll();
        
        
        Map<Integer, String> serviceList = new LinkedHashMap<>();
        for (Service s : services) {
            serviceList.put(s.getServiceId(), s.getServiceType());
        }

        model.addAttribute("editedService", service);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminServicesController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }

}
