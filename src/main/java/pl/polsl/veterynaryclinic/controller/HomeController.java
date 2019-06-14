package pl.polsl.veterynaryclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.veterynaryclinic.constants.Banner;
import pl.polsl.veterynaryclinic.constants.UserType;
import pl.polsl.veterynaryclinic.controller.admin.AdminModuleController;
import pl.polsl.veterynaryclinic.controller.client.pets.ClientPetsController;
import pl.polsl.veterynaryclinic.controller.worker.appointments.WorkerFutureAppointmentsController;
import pl.polsl.veterynaryclinic.controller.worker.appointments.WorkerPresentAppointmentController;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.repository.UserRepository;
import pl.polsl.veterynaryclinic.validator.UserLoginValidator;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("loggedUser")
public class HomeController {

    public static final String PAGE_ADDRESS = "/";

    private static final String PAGE_SOURCE = "home";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginValidator userLoginValidator;

    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userLoginValidator);
    }

    @PostMapping(PAGE_ADDRESS)
    public String postHome(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", Banner.getBanner(Banner.NOT_LOGGED_BANNER, PAGE_ADDRESS));
            return PAGE_SOURCE;
        }
        User loggedUser = userRepository.findByLogin(user.getLogin());
        model.addAttribute("loggedUser", loggedUser);
        return "redirect:" + PAGE_ADDRESS;
        
    }

    @GetMapping(PAGE_ADDRESS)
    public String getHome(@ModelAttribute("user") User user, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null)
            switch (loggedUser.getUserType()) {
                case UserType.CLIENT_ID:
                    return "redirect:" + ClientPetsController.PAGE_ADDRESS;
                case UserType.DOCTOR_ID:
                    return "redirect:" + WorkerPresentAppointmentController.PAGE_ADDRESS;
                case UserType.ADMIN_ID:
                	return "redirect:" + AdminModuleController.PAGE_ADDRESS;
                	//return "redirect:admin/admin_module";
            }
        else
            model.addAttribute("banner", Banner.getBanner(Banner.NOT_LOGGED_BANNER, PAGE_ADDRESS));
        return PAGE_SOURCE;
    }
}
