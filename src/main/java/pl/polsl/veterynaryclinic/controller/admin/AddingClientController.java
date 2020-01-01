package pl.polsl.veterynaryclinic.controller.admin;

import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import pl.polsl.veterynaryclinic.validator.admin.AddingClientValidator;

@Controller
public class AddingClientController {

	private static final String PAGE_ADDRESS = "/admin/admin_clients_add";

    private static final String PAGE_SOURCE = "/admin/admin_clients_add";

    @Autowired
	private UserDao userDao;
    
    @Autowired
    private AddingClientValidator addingClientValidator;

    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addingClientValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddingClient(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminClientsController.PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        user.setUserType(UserType.CLIENT_ID);
        userDao.addUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserType(), user.getLogin(), user.getPassword(), user.getPhoneNumber());
        return "redirect:/admin/admin_clients";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddingClient(@ModelAttribute("user") User user, @SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminClientsController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
