package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.dao.UserDao;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.validator.admin.AdminClientsEditValidator;

@Controller
public class AdminClientsEditController {

	private static final String PAGE_ADDRESS = "/admin/admin_clients_edit";

    private static final String PAGE_SOURCE = "/admin/admin_clients_edit";

    @Autowired
	private UserDao userDao;
    
    @Autowired
    private AdminClientsEditValidator adminClientsEditValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(adminClientsEditValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAdminClientsEdit(@SessionAttribute("editedClient") User editedClient, @ModelAttribute("newEditedClient") @Validated User newEditedClient, BindingResult bindingResult, Model model, @RequestParam String action) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editedClient", editedClient);
                model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminClientsController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            userDao.editClient(editedClient.getUserId(), newEditedClient.getFirstName(), newEditedClient.getLastName(), editedClient.getUserType(), newEditedClient.getLogin(), newEditedClient.getPassword(), newEditedClient.getPhoneNumber());
            return "redirect:/admin/admin_clients";
        }
        return "redirect:/admin/admin_clients";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminClientsEdit(@SessionAttribute("loggedUser") User loggedUser, @SessionAttribute("editedClient") User editedClient, @ModelAttribute("newEditedClient") User newEditedClient, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        if (editedClient.getUserId() == null)
            return "redirect:/admin/admin_clients";
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("editedClient", editedClient);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminClientsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
