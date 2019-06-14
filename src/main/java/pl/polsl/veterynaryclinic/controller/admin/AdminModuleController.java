package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.*;
import pl.polsl.veterynaryclinic.model.User;

@Controller
public class AdminModuleController {
	
	public static final String PAGE_ADDRESS = "admin/admin_module";

    private static final String PAGE_SOURCE = "admin/admin_module";
    

	@GetMapping("admin/admin_module")
	public String getAdminModule(@SessionAttribute("loggedUser")User user, Model model) throws ServletRequestBindingException {
		if (!user.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
		
		
		
        model.addAttribute("user", user);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminModuleController.PAGE_ADDRESS));
		return PAGE_SOURCE;
	}
	
}
