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
import pl.polsl.veterynaryclinic.validator.admin.AddingWorkerValidator;

@Controller
public class AddingWorkersController {

	private static final String PAGE_ADDRESS = "/admin/admin_workers_add";

    private static final String PAGE_SOURCE = "/admin/admin_workers_add";

    @Autowired
	private UserDao userDao;
    
    @Autowired
    private AddingWorkerValidator addingWorkerValidator;

    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addingWorkerValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAddingWorkers(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminWorkersController.PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        user.setUserType(UserType.DOCTOR_ID);
        userDao.addUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserType(), user.getLogin(), user.getPassword(), user.getPhoneNumber());
        return "redirect:/admin/admin_workers";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAddingWorkers(@ModelAttribute("user") User user, @SessionAttribute("loggedUser")User loggedUser, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminWorkersController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
