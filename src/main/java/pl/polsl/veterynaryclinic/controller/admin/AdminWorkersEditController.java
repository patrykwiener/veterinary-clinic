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
import pl.polsl.veterynaryclinic.validator.admin.AdminWorkersEditValidator;

@Controller
public class AdminWorkersEditController {

	private static final String PAGE_ADDRESS = "/admin/admin_workers_edit";

    private static final String PAGE_SOURCE = "/admin/admin_workers_edit";

    @Autowired
	private UserDao userDao;
    
    @Autowired
    private AdminWorkersEditValidator adminWorkersEditValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(adminWorkersEditValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postAdminWorkersEdit(@SessionAttribute("editedWorker") User editedWorker, @ModelAttribute("newEditedWorker") @Validated User newEditedWorker, BindingResult bindingResult, Model model, @RequestParam String action) {
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editedWorker", editedWorker);
                model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminWorkersController.PAGE_ADDRESS));
                return PAGE_SOURCE;
            }
            userDao.editClient(editedWorker.getUserId(), newEditedWorker.getFirstName(), newEditedWorker.getLastName(), editedWorker.getUserType(), newEditedWorker.getLogin(), newEditedWorker.getPassword(), newEditedWorker.getPhoneNumber());
            return "redirect:/admin/admin_workers";
        }
        return "redirect:/admin/admin_workers";
    }

    @GetMapping(PAGE_ADDRESS)
    public String getAdminWorkersEdit(@SessionAttribute("loggedUser") User loggedUser, @SessionAttribute("editedWorker") User editedWorker, @ModelAttribute("newEditedWorker") User newEditedWorker, Model model) throws ServletRequestBindingException {
        if (!loggedUser.getUserType().equals(UserType.ADMIN_ID))
            throw new ServletRequestBindingException("");
        if (editedWorker.getUserId() == null)
            return "redirect:/admin/admin_workers";
        
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("editedWorker", editedWorker);
        model.addAttribute("banner", Banner.getBanner(Banner.ADMIN_BANNER, AdminWorkersController.PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
	
}
